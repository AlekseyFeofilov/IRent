package com.example.shift

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shift.card.own.OwnFragment.Companion.getCardsById
import com.example.shift.api.CardApi
import com.example.shift.card.info.CardInfo
import com.example.shift.card.info.CardInfoRecyclerAdapter
import com.example.shift.card.info.CardOption
import com.example.shift.companion.IrentApp
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.databinding.FragmentRentedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RentedFragment : Fragment(), CardOption {
    private lateinit var binding: FragmentRentedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentedBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = CardInfoRecyclerAdapter(getRentedCards(), this)
        return binding.root
    }

    fun showMessage(message: String) {
        binding.messageTextView.text = message
    }

    private fun getRentedCards(): List<CardInfo> {
        var rentedCards: List<Long> = emptyList()
        showMessage("")

        IrentApp.retrofit
            .create(CardApi::class.java)
            .getRentedCards(user!!.id)
            .enqueue(object : Callback<List<Long>> {
                override fun onResponse(
                    call: Call<List<Long>>,
                    response: Response<List<Long>>
                ) {
                    if (response.body() == null) {
                        showMessage(getString(R.string.something_went_wrong_try_again))
                        return
                    }

                    if (response.body()!!.isEmpty()) showMessage("You haven't own cards yet")

                    rentedCards = response.body()!!
                }

                override fun onFailure(call: Call<List<Long>>, t: Throwable) {
                    Log.e("Backend", t.toString())
                }
            })

        return getCardsById(rentedCards)
    }

    override fun editCard(cardInfo: CardInfo) {

    }

    override fun deleteCard(cardInfo: CardInfo) {

    }

    override fun endCardRenting(cardInfo: CardInfo) {

    }
}