package com.example.newsapp

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnNewsClicked {

     private lateinit var adapter:NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this)
        recView.adapter = adapter
        fetchData()

    }
    private fun fetchData(){
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"

        val jsonObjectRequest = JsonObjectRequest(
            url , null, {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<NewsModel>()
                for (i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsModel(newsJsonObject.getString("title") ,
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                adapter.updateNews(newsArray)
            },

            {

            }

        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onClick(item: NewsModel) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent:CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}
