package com.ergea.ridsnewsapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.data.repository.NewsRepository
import com.ergea.ridsnewsapp.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _news =
        MutableStateFlow<Resource<List<News>>>(Resource.Loading())
    val news = _news.asStateFlow()

    fun getNews(sources: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getNews(sources, null).collect {
            _news.value = it
        }
    }
}