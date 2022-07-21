package com.example.shift.card.own

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shift.R
import com.example.shift.api.CardApi
import com.example.shift.card.info.CardAndUserId
import com.example.shift.card.info.CardInfo
import com.example.shift.card.info.CardInfoRecyclerAdapter
import com.example.shift.card.info.CardOption
import com.example.shift.companion.IrentApp
import com.example.shift.companion.SharedPreferencesObject.Companion.user
import com.example.shift.databinding.FragmentOwnBinding
import com.example.shift.main.NavControllerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnFragment : Fragment(), CardOption {
    private lateinit var binding: FragmentOwnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
/*
        viewLifecycleOwner.lifecycleScope.launch {
            binding.recyclerView.adapter = CardInfoRecyclerAdapter(getOwnCards(), this@OwnFragment)
        }
*/
        val thread = Thread {
            try {
                binding.recyclerView.adapter = CardInfoRecyclerAdapter(getOwnCards(), this@OwnFragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()

        return binding.root
    }

    fun showMessage(message: String) {
        binding.messageTextView.text = message
    }

    private fun getOwnCards(): List<CardInfo> {
        showMessage("")

        val ownCardId = IrentApp.retrofit
            .create(CardApi::class.java)
            .getOwnCards(UserId(user!!.id))
            .execute()
            .body()

        if (ownCardId == null) {
            showMessage(getString(R.string.something_went_wrong_try_again))
            return emptyList()
        }

        if (ownCardId.isEmpty()) showMessage(getString(R.string.ypu_haven_t_own_cards_yet))

        return getCardsById(ownCardId)
    }

    companion object {
        fun getCardsById(cardsId: List<Long>): List<CardInfo> {
            val ownCards = mutableListOf<CardInfo>()

            cardsId.forEach {
                IrentApp.retrofit
                    .create(CardApi::class.java)
                    .getCard(it)
                    .enqueue(object : Callback<CardInfo> {
                        override fun onResponse(
                            call: Call<CardInfo>,
                            response: Response<CardInfo>
                        ) {
                            if (response.body() == null) return

                            ownCards.add(response.body()!!)
                        }

                        override fun onFailure(call: Call<CardInfo>, t: Throwable) {
                            Log.e("Backend", t.toString())
                        }
                    })
            }

            return ownCards
        }
    }

    override fun editCard(cardInfo: CardInfo) {

    }

    override fun deleteCard(cardInfo: CardInfo) {
        IrentApp.retrofit
            .create(CardApi::class.java)
            .deleteCard(CardAndUserId(cardInfo.id, user!!.id))
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if (response.body() == null || response.body() == false) {
                        showMessage(getString(R.string.something_went_wrong_try_again))
                        return
                    }

                    (requireActivity() as NavControllerActivity).goTo(R.id.ownFragment)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("Backend", t.toString())
                }
            })
    }

    override fun endCardRenting(cardInfo: CardInfo) {

    }
}