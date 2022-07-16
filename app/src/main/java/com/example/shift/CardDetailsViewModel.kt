package com.example.shift.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shift.api.FeedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(id: Long) : ViewModel() {
    private val cardRepository = CardRepository()

    private val _cardItem: MutableStateFlow<FeedItem?> = MutableStateFlow(null)
    val cardItem: StateFlow<FeedItem?> = _cardItem.asStateFlow()

    init {
        viewModelScope.launch {
            _cardItem.value = cardRepository.fetchCard(id)
        }
    }
}

class CrimeDetailsViewModelFactory(private val id: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CardDetailsViewModel(id) as T
}