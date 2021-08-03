package com.example.newsapp.repositories

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.services.ApiProvider
import retrofit2.Response

class NewsRepository() {

    // Api calls
    suspend fun getTopHeadlinesNews(page: Int): Response<NewsResponse> {
        return ApiProvider.retrofit.getTopHeadlinesNews(page = page)
    }


}