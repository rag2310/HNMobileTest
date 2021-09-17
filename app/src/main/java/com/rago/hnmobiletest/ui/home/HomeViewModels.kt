package com.rago.hnmobiletest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModels @Inject constructor() : ViewModel() {

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
    }
}