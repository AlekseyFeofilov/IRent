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

    private val _photoItems: MutableStateFlow<List<FeedItem>> = MutableStateFlow(emptyList())

    val photoItems: StateFlow<List<FeedItem>>
    get() = _photoItems.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val items = feedRepository.fetchFeeds()
                Log.d(TAG, "Items received: $items")
                _photoItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }
}