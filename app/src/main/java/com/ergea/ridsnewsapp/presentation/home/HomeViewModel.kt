package com.ergea.ridsnewsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _category =
        MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val category = _category.asStateFlow()

    fun getCategories() = viewModelScope.launch(Dispatchers.IO) {
        repository.getCategories().collect {
            _category.value = it
        }
    }
}