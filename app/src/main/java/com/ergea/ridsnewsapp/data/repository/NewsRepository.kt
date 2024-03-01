package com.ergea.ridsnewsapp.data.repository

import com.ergea.ridsnewsapp.common.Resource
import com.ergea.ridsnewsapp.common.proceedFlow
import com.ergea.ridsnewsapp.data.remote.api.service.NewsService
import com.ergea.ridsnewsapp.model.DummyCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface NewsRepository {

    fun getCategories(): Flow<Resource<List<String>>>

}

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsService
) : NewsRepository {
    override fun getCategories(): Flow<Resource<List<String>>> =
        proceedFlow {
            DummyCategory.generateCategory()
        }


}