package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsAdapter

    private var mcategory = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     main()
    }
    private fun fetchdata()  {
        val url="https://saurav.tech/NewsAPI/top-headlines/category/$mcategory/in.json"
        val jsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
                Response.Listener{
                    val Newsjsonarray = it.getJSONArray("articles")
                    val NewsArray = ArrayList<News>()
                    for (i in 0 until Newsjsonarray.length()) {
                        val NewsjsonObject = Newsjsonarray.getJSONObject(i)
                        val news = News(
                                NewsjsonObject.getString("title"),
                                NewsjsonObject.getString("author"),
                                NewsjsonObject.getString("urlToImage"),
                                NewsjsonObject.getString("url"),
                                NewsjsonObject.getString("publishedAt")

                        )
                        NewsArray.add(news)
                    }
                    mAdapter.UpdateNews(NewsArray)
                },
                Response.ErrorListener {
                    Toast.makeText(this,"Error occured",Toast.LENGTH_LONG).show()
                })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun main(){
        recyclerView.layoutManager= LinearLayoutManager(this)
        fetchdata()
        mAdapter= NewsAdapter(this)
        recyclerView.adapter = mAdapter}


    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

    fun Business(view: View) {
        mcategory="business"
        main()
    }
    fun Sports(view: View) {
        mcategory="sports"
        main()
    }
    fun Entertainment(view: View) {
        mcategory="entertainment"
        main()
    }

    fun Science(view: View) {
        mcategory="science"
        main()
    }
    fun Health(view: View) {
        mcategory="health"
        main()
    }
    fun Technology(view: View) {
        mcategory="technology"
        main()
    }
}