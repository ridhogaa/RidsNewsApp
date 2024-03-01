package com.ergea.ridsnewsapp.data.remote.api.service

import com.ergea.ridsnewsapp.data.remote.api.model.GetNewsResponse
import com.ergea.ridsnewsapp.data.remote.api.model.GetSourceResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("sources") sources: String? = null,
        @Query("q") q: String? = null
    ): GetNewsResponse

    @GET("top-headlines/sources")
    suspend fun getSource(
        @Query("category") category: String? = null,
        @Query("q") q: String? = null
    ): GetSourceResponse
}