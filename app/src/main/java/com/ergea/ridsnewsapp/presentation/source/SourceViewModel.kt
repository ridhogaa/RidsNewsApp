package com.ergea.ridsnewsapp.presentation.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.data.repository.NewsRepository
import com.ergea.ridsnewsapp.model.News
import com.ergea.ridsnewsapp.model.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _source =
        MutableStateFlow<Resource<List<Source>>>(Resource.Loading())
    val source = _source.asStateFlow()

    fun getSource(category: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSource(category, null).collect {
            _source.value = it
        }
    }
}