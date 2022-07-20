package com.example.shift.card.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.shift.R
import com.example.shift.databinding.FragmentCardDetailsBinding
import kotlinx.coroutines.launch

class CardDetailsFragment : Fragment() {
    private var _binding: FragmentCardDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: CardDetailsFragmentArgs by navArgs()

    private val cardDetailsViewModel: CardDetailsViewModel by viewModels() {
        CrimeDetailsViewModelFactory(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cardDetailsViewModel.cardItem.collect{
                    binding.apply {
                        cardName.text = it?.cardName
                        cardPrice.text = it?.cardPrice.toString()
                        try {
                            cardPhoto.load(it?.imagesURL?.get(0))
                        } catch (ex: Exception) {
                            cardPhoto.setImageResource(R.drawable.ic_no_photo)
                        }
                        cardDescription.text = it?.description
                        cardCategory.text = "Категория: " + it?.category

                        if (it?.rent == true)
                            isRent.text = "В аренде"
                        else isRent.text = "Не в аренде"

                        cardOwner.text = it?.ownerName + " " + it?.ownerSurname
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}