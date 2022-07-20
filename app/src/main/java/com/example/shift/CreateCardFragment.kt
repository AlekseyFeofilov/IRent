package com.example.shift

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shift.api.CardApi
import com.example.shift.card.data.NewCard
import com.example.shift.card.info.CardInfo
import com.example.shift.companion.IrentApp
import com.example.shift.databinding.FragmentCreateCardBinding
import com.example.shift.main.MainActivity
import com.example.shift.main.NavControllerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCardFragment : Fragment() {
private lateinit var binding: FragmentCreateCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCardBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener(createCard)
        return binding.root
    }

    private fun showWarning(warning: String){
        binding.warningTextView.text = warning
    }

    private fun checkCompletion(newCard: NewCard): Boolean{
        newCard.apply {
            when {
                category.isEmpty() -> showWarning(getString(R.string.enter_category))
                title.isEmpty() -> showWarning(getString(R.string.enter_title))
                price.isEmpty() -> showWarning(getString(R.string.enter_price))
                description.isEmpty() -> showWarning(getString(R.string.enter_description))
                address.isEmpty() -> showWarning(getString(R.string.enter_address))
                else -> return true
            }
        }

        return false
    }

    private val createCard = View.OnClickListener {
        showWarning("")

        val category = binding.categoryEditText.text.toString()
        val title = binding.titleEditText.text.toString()
        val price = binding.priceEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val address = binding.addressEditText.text.toString()

        val newCard = NewCard(title, category, price, description, address)

        if(!checkCompletion(newCard)){
            return@OnClickListener
        }

        createCard(newCard)
    }

    private fun createCard(newCard: NewCard){
        IrentApp.retrofit
            .create(CardApi::class.java)
            .createCard(newCard)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if (response.body() == null || response.body() == false) {
                        showWarning(getString(R.string.something_went_wrong_try_again))
                        return
                    }

                    (requireActivity() as NavControllerActivity).goTo(R.id.ownFragment)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.e("Backend", t.toString())
                }
            })
    }
}