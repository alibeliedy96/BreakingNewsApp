package com.example.newsapp.services

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesNews(
        @Query("country") country: String = "us",
        @Query("page") page: Int =1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsResponse>


}