package com.ergea.ridsnewsapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.data.repository.NewsRepository
import com.ergea.ridsnewsapp.model.News
import com.ergea.ridsnewsapp.model.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _news =
        MutableStateFlow<Resource<List<News>>>(Resource.Empty())
    val news = _news.asStateFlow()

    private val _source =
        MutableStateFlow<Resource<List<Source>>>(Resource.Empty())
    val source = _source.asStateFlow()

    fun searchSourceAndNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getNews(null, q = query)
            .collectLatest {
                _news.value = it
            }
        repository.getSource(null, q = query)
            .collectLatest {
                _source.value = it
            }
    }
}