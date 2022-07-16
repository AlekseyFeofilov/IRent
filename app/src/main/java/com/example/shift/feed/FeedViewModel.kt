package com.example.shift.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift.api.FeedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "FeedViewModel"

class FeedViewModel: ViewModel() {
    private val feedRepository = FeedRepository()

    private val _feedItems: MutableStateFlow<List<FeedItem>> = MutableStateFlow(emptyList())
    val feedItems: StateFlow<List<FeedItem>>
    get() = _feedItems.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val items = feedRepository.fetchFeeds()
                Log.d(TAG, "Items received: $items")
                _feedItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }
}