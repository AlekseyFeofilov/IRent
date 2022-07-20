package com.example.shift.card.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shift.R
import com.example.shift.api.FeedItem
import com.example.shift.databinding.ListItemFeedBinding

class FeedViewHolder(private val binding: ListItemFeedBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(feedItem: FeedItem, onCardClicked: (id: Long) -> Unit) {
        binding.apply {
            try {
                cardPhoto.load(feedItem.imagesURL[0])
            } catch (ex: Exception) {
                cardPhoto.setImageResource(R.drawable.ic_no_photo)
            }
            cardName.text = feedItem.cardName
            cardPrice.text = feedItem.cardPrice.toString()

            root.setOnClickListener {
                onCardClicked(feedItem.id)
            }
        }
    }
}

class FeedListAdapter(
    private val feedItems: List<FeedItem>,
    private val onCardClicked: (id: Long) -> Unit,
) : RecyclerView.Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFeedBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = feedItems[position]
        holder.bind(item, onCardClicked)
    }

    override fun getItemCount() = feedItems.size
}