package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import android.graphics.drawable.Drawable
import android.widget.ProgressBar
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target

class NewsAdapter( private val listener : NewsItemClicked): RecyclerView.Adapter<NewsViewholder>() {

    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.itemnews,parent,false)
        val viewholder =NewsViewholder(view)
        view.setOnClickListener{
        listener.onItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {
           holder.Progress.visibility= View.VISIBLE
        val currentitem=items[position]
        holder.titleView.text=currentitem.title
        holder.authorView.text=currentitem.author+" - "+currentitem.publishedAt
        Glide.with(holder.itemView.context).load(currentitem.urlToImage).listener(object :RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.Progress.visibility=View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.Progress.visibility=View.GONE
                return false
            }

        }).into(holder.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun UpdateNews(updatednews:ArrayList<News>)
    {
           items.clear()
        items.addAll(updatednews)

        notifyDataSetChanged()
    }
}
interface NewsItemClicked{
    fun onItemClicked(item:News)
}


class NewsViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
 val titleView:TextView= itemView.findViewById(R.id.title)
    val authorView :TextView=itemView.findViewById(R.id.author)
    val image:ImageView=itemView.findViewById(R.id.imageView)
    val Progress :ProgressBar=itemView.findViewById(R.id.Sign)
}