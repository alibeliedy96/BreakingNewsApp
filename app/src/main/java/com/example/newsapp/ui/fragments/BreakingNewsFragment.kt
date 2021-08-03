package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.resources.NewsResource
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsapp.viewmodels.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news), KodeinAware {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override val kodein by closestKodein()
    private val factory: NewsViewModelProviderFactory by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
        initNewsRecyclerView()

        viewModel.newsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NewsResource.Success -> {
                    progressBarStatus(false)
                    tryAgainStatus(false)
                    newsAdapter.differ.submitList(response.data!!.articles)
                }
                is NewsResource.Error -> {
                    tryAgainStatus(true, response.message!!)
                    progressBarStatus(false)
                }
                is NewsResource.Loading -> {
                    tryAgainStatus(false)
                    progressBarStatus(true)
                }
            }
        })

        tryAgainButton.setOnClickListener { viewModel.getBreakingNews() }
    }


    private fun initNewsRecyclerView() {
        newsAdapter = NewsAdapter()
        breakingNewsRecyclerView.adapter = newsAdapter
    }

    private fun progressBarStatus(status: Boolean) {
        progressBar.visibility = if (status) VISIBLE else GONE
    }

    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            tryAgainMessage.text = message
            tryAgainLayout.visibility = View.VISIBLE
        } else {
            tryAgainLayout.visibility = View.GONE
        }
    }
}
