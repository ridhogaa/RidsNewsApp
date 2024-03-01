package com.ergea.ridsnewsapp.data.repository

import com.ergea.ridsnewsapp.data.remote.api.service.NewsService
import javax.inject.Inject


interface NewsRepository {


}

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService
) : NewsRepository {

}