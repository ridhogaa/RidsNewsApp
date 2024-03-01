package com.ergea.ridsnewsapp.data.remote.api.service

import com.ergea.ridsnewsapp.data.remote.api.model.GetNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines?country=id")
    suspend fun getNews(@Query("category") category: String): GetNewsResponse
}