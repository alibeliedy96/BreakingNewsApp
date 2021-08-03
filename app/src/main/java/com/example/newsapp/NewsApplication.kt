package com.example.newsapp

import android.app.Application
import com.example.newsapp.repositories.NewsRepository
import com.example.newsapp.services.ApiProvider
import com.example.newsapp.viewmodels.NewsViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication : Application() , KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))
        bind() from singleton { ApiProvider() }
        bind() from singleton { NewsRepository() }
        bind() from provider { NewsViewModelProviderFactory(instance(), this@NewsApplication) }
    }
}