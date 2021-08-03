package com.example.newsapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.ui.ArticleActivity
import com.example.newsapp.utils.Handler.Companion.instance
import com.example.newsapp.utils.loadImage
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = (activity as ArticleActivity).article

        initArticleData(article)

        readMoreButton.setOnClickListener {

            // open in webView fragment
            //findNavController().navigate(R.id.action_articleFragment_to_webArticleFragment)
            //open in web url
            val urlIntent = Intent(Intent.ACTION_VIEW)
            urlIntent.data = Uri.parse(article.url)
            startActivity(urlIntent)
        }
    }

    private fun initArticleData(article: Article) {
        articleImage.loadImage(article.urlToImage)
        articleTitle.text = article.title
        articleDescription.text = article.description
        articleDate.text = article.publishedAt
    }
}