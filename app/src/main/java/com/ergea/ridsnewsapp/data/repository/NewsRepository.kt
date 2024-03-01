package com.ergea.ridsnewsapp.data.repository

import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.common.proceedFlow
import com.ergea.ridsnewsapp.data.remote.api.model.mapToEntity
import com.ergea.ridsnewsapp.data.remote.api.service.NewsService
import com.ergea.ridsnewsapp.model.DummyCategory
import com.ergea.ridsnewsapp.model.News
import com.ergea.ridsnewsapp.model.Source
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface NewsRepository {

    fun getCategories(): Flow<Resource<List<String>>>

    suspend fun getNews(sources: String?, q: String?): Flow<Resource<List<News>>>

    suspend fun getSource(category: String?, q: String?): Flow<Resource<List<Source>>>
}

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService
) : NewsRepository {
    override fun getCategories(): Flow<Resource<List<String>>> =
        proceedFlow {
            DummyCategory.generateCategory()
        }

    override suspend fun getNews(sources: String?, q: String?): Flow<Resource<List<News>>> =
        proceedFlow {
            service.getNews(sources, q).articles?.map { it.mapToEntity() } ?: emptyList()
        }

    override suspend fun getSource(category: String?, q: String?): Flow<Resource<List<Source>>> =
        proceedFlow {
            service.getSource(category, q).sources?.map { it.mapToEntity() } ?: emptyList()
        }

}