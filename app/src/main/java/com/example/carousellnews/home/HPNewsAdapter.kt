package com.example.carousellnews.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carousellapp.databinding.RvNewsItemsBinding
import com.example.carousellnews.data.NewsItem
import com.example.carousellnews.util.TimeFormatter

class HPNewsAdapter: ListAdapter<NewsItem,HPNewsAdapter.HPNewsViewHolder>(NewsItemDiffCallback) {
    class HPNewsViewHolder(private val rvListBinding: RvNewsItemsBinding): RecyclerView.ViewHolder(rvListBinding.root) {

        fun bind(newsItem: NewsItem) {
            rvListBinding.apply {
                Glide.with(rvListBinding.root).load(newsItem.bannerUrl).into(ivNews)
                tvTitle.text = newsItem.title
                tvDescription.text = newsItem.description
                tvTimeStamp.text = TimeFormatter.convertTimestampToAgo(newsItem.timeCreated)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HPNewsViewHolder {
       return HPNewsViewHolder(RvNewsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HPNewsViewHolder, position: Int) {
        holder.bind(newsItem = getItem(position))
    }
}

object NewsItemDiffCallback: DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
       return oldItem == newItem
    }
}