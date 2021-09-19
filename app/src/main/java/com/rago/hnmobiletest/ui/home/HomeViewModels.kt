package com.rago.hnmobiletest.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rago.hnmobiletest.data.model.Hit
import com.rago.hnmobiletest.data.network.AlgoliaQuery
import com.rago.hnmobiletest.data.network.CustomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModels @Inject constructor(
    private val algoliaQuery: AlgoliaQuery
) : ViewModel() {

    private val _listHit = MutableLiveData<List<Hit>>()
    val listHit: LiveData<List<Hit>> = _listHit


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getArticles()
    }

    fun refresh() {
        getArticles()
    }

    fun delete(id: Int) {
    }

    private fun showError(t: Throwable) {
        when (t) {
            is SocketTimeoutException -> Log.d("Error Retrofit", "showError: Connection Timeout")
            is IOException -> Log.d("Error Retrofit", "showError: Timeout")
            else -> Log.d("Error Retrofit", "showError: Network Error :: ${t.localizedMessage}")
        }
    }

    private fun getArticles() {
        _loading.value = true
        algoliaQuery.query().enqueue(object : Callback<CustomResponse> {
            override fun onResponse(
                call: Call<CustomResponse>,
                response: Response<CustomResponse>
            ) {
                val list = response.body()
                list?.let {
                    _listHit.value = it.hits
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