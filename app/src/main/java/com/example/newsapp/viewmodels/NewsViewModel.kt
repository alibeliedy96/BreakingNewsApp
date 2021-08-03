package com.example.newsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.NewsApplication
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repositories.NewsRepository
import com.example.newsapp.resources.NewsResource
import com.example.newsapp.utils.hasInternetConnection
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository, app: Application) :
    AndroidViewModel(app) {

    private val newsDataTemp = MutableLiveData<NewsResource<NewsResponse>>()
    val newsData = MutableLiveData<NewsResource<NewsResponse>>()
    private var breakingNewsPage = 1


    init {
        getBreakingNews()
    }

    fun getBreakingNews() = viewModelScope.launch {
        newsData.postValue(NewsResource.Loading())
        try {
            if (hasInternetConnection<NewsApplication>()) {
                val response = newsRepository.getTopHeadlinesNews(breakingNewsPage)
                if (response.isSuccessful) {
                    newsDataTemp.postValue(NewsResource.Success(response.body()!!))
                    newsData.postValue(NewsResource.Success(response.body()!!))
                } else {
                    newsData.postValue(NewsResource.Error(response.message()))
                }
            } else {
                newsData.postValue(NewsResource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            newsData.postValue(NewsResource.Error(t.message!!))
        }
    }


}