package com.rago.hnmobiletest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rago.hnmobiletest.data.model.Hit
import com.rago.hnmobiletest.data.network.AlgoliaQuery
import com.rago.hnmobiletest.data.network.CustomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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


    private val _listArticles = MutableLiveData<List<String>>()
    val listArticles: LiveData<List<String>> = _listArticles

    init {
        _listArticles.value = listOf("Texto")
    }

    fun refresh() {
        val current = _listArticles.value
        val replacement = mutableListOf<String>()
        current!!.forEach {
            replacement.add(it)
        }
        replacement.add("Text ${current.size}")
        _listArticles.value = replacement
        algoliaQuery.query().enqueue(
            object : Callback<CustomResponse> {

                override fun onResponse(
                    call: Call<CustomResponse>,
                    response: Response<CustomResponse>
                ) {
                    val list = response.body()
                    list!!.hits.forEach {
                        println("author ${it.author}")
                    }
                }

                override fun onFailure(call: Call<CustomResponse>, t: Throwable) {
                    when (t) {
                        is SocketTimeoutException -> "Connection Timeout"
                        is IOException -> "Timeout"
                        else -> "Network Error :: ${t.localizedMessage}"
                    }
                }

            }
        )
    }

    fun delete(id: Int) {
        val text = _listArticles.value!!.get(id)
        val current = _listArticles.value
        val replacement = mutableListOf<String>()
        current!!.forEach {
            if (text != it)
                replacement.add(it)
        }
        _listArticles.value = replacement
    }
}