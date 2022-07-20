package com.example.shift.card.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shift.databinding.ItemCardInfoBinding

class CardInfoRecyclerAdapter(
    private var cards: List<CardInfo>,
    private val cardOption: CardOption
) :
    RecyclerView.Adapter<CardInfoRecyclerAdapter.CardInfoViewHolder>() {

    class CardInfoViewHolder(val binding: ItemCardInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        return CardInfoViewHolder(
            ItemCardInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        val card = cards[position]

        holder.binding.apply {
            titleTextView.text = card.title
            ownerTextView.text = "Owner: ${card.ownerName} ${card.ownerSurname}"
            priceTextView.text = "${card.price}P"
            termTextView.text = card.term

            if (card.status == null) statusTextView.visibility = View.GONE
            else statusTextView.text = if (card.status!!) "In rent" else "free"

            itemConstraintLayout.setOnClickListener {
                optionLinearLayout.visibility = View.GONE
            }

            optionImageButton.setOnClickListener {
                optionLinearLayout.visibility = View.VISIBLE
            }

            editCardTextView.setOnClickListener{
                cardOption.editCard(card)
            }

            deleteCardTextView.setOnClickListener {
                cardOption.deleteCard(card)
            }

            endCardRentingTextView.setOnClickListener {
                cardOption.endCardRenting(card)
            }
        }
    }

    override fun getItemCount() = cards.size
}