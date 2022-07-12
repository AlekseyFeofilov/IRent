package com.example.shift.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shift.api.FeedItem
import com.example.shift.databinding.ListItemFeedBinding

class FeedViewHolder(private val binding: ListItemFeedBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(feedItem: FeedItem) {
        binding.apply {
            cardPhoto.load(feedItem.cardPhotoUrl)
            cardName.text = feedItem.cardName
            cardPrice.text = feedItem.cardPrice
        }
    }
}

class FeedListAdapter(private val feedItems: List<FeedItem>): RecyclerView.Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFeedBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = feedItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = feedItems.size
}