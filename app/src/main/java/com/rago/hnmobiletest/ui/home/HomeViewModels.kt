package com.rago.hnmobiletest.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rago.hnmobiletest.data.model.CustomResponse
import com.rago.hnmobiletest.data.model.ExcludeArticle
import com.rago.hnmobiletest.data.model.Hit
import com.rago.hnmobiletest.data.network.AlgoliaQuery
import com.rago.hnmobiletest.data.repository.ExcludeArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


//ViewModel para la logica de la pantalla principal
@HiltViewModel
class HomeViewModels @Inject constructor(
    private val algoliaQuery: AlgoliaQuery,
    private val excludeArticleRepository: ExcludeArticleRepository
) : ViewModel() {

    //Lista que se mostrara en la pantalla principal
    private val _listHit = MutableLiveData<List<Hit>>()
    val listHit: LiveData<List<Hit>> = _listHit

    //Boolean que valida la aparicion del circulo de progreso indefinido
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        //Obtenemos los articulos cuando entramos por primera vez en la pantalla principal
        getArticles()
    }

    fun refresh() {
        //Obtenemos los articulos cuando refrescamos con el SwipeRefreshLayout en la pantalla principal
        getArticles()
    }

    //Elimina el articulo
    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //validamos que la lista no este nula
            _listHit.value?.let { list ->
                //obtenemos el elemento a borar mediante el index de la posicion de la lista
                val hit = list[id]
                hit.storyId?.let { storyId ->

                    val excludeArticle = ExcludeArticle(storyId = storyId)

                    //Insertamos en la tabla EXCLUDE_ARTICLE para no volver a mostrar dicho articulo
                    excludeArticleRepository.insert(excludeArticle = excludeArticle)
                    _listHit.value?.let { current ->
                        val replacement = mutableListOf<Hit>()
                        current.forEach { hit ->
                            if (hit.storyId != excludeArticle.storyId) {
                                //Agregamos unicamente los articulos que no esten siendo borrados
                                replacement.add(hit)
                            }
                        }
                        //Actualizamos la lista para que se aplique el cambio en la UI
                        _listHit.postValue(replacement)
                    }
                }
            }
        }
    }

    //Muestra los errores de conexion del retrofit
    private fun showError(t: Throwable) {
        when (t) {
            is SocketTimeoutException -> Log.d("Error Retrofit", "showError: Connection Timeout")
            is IOException -> Log.d("Error Retrofit", "showError: Timeout")
            else -> Log.d("Error Retrofit", "showError: Network Error :: ${t.localizedMessage}")
        }
    }

    //Funcion principal para obtener la lista de articulos de noticias del API
    private fun getArticles() {
        _loading.value = true

        algoliaQuery.query().enqueue(object : Callback<CustomResponse> {
            override fun onResponse(
                call: Call<CustomResponse>,
                response: Response<CustomResponse>
            ) {
                val list = response.body()
                list?.let { customResponse ->
                    viewModelScope.launch(Dispatchers.IO) {

                        //Realizamos la consulta de todos los articulos borrados
                        excludeArticleRepository.allExcludeArticle.collect { listDb ->
                            val mutableList: MutableList<Hit> = list.hits as MutableList<Hit>
                            if (listDb.isEmpty()) {
                                //si no existe nada, se actualiza la lista con el api
                                _listHit.postValue(customResponse.hits)
                            } else {
                                //si hay datos en la tabla, se iteran las dos listas para eliminar
                                    // los articulos excluidos
                                listDb.forEach { excludeArticle ->
                                    val iterator = mutableList.iterator()
                                    while (iterator.hasNext()) {
                                        val item = iterator.next()
                                        if (excludeArticle.storyId == item.storyId)
                                            iterator.remove()
                                    }
                                }
                                _listHit.postValue(mutableList)
                            }
                        }
                    }
                    _loading.value = false
                }
            }

            override fun onFailure(call: Call<CustomResponse>, t: Throwable) {
                showError(t)
                _loading.value = false
            }

        })

    }
}