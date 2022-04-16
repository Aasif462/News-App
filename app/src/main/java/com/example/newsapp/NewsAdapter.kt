package com.example.newsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener:OnNewsClicked) : RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    private val items :ArrayList<NewsModel> = ArrayList()

    inner class NewsHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.titles)
        val image: ImageView = itemView.findViewById(R.id.image)
        val author:TextView = itemView.findViewById(R.id.author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_layout , parent , false)
        val viewHolder = NewsHolder(view)
        view.setOnClickListener {
            listener.onClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val model = items[position]
        holder.title.text = model.title
        Glide.with(holder.image.context).load(model.imageUrl).into(holder.image)
        holder.author.text = model.author
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedItems:ArrayList<NewsModel>){
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

interface OnNewsClicked{
    fun onClick(item:NewsModel)
}