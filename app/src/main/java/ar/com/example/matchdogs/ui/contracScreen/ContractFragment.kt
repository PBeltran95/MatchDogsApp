package ar.com.example.matchdogs.ui.contracScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.databinding.FragmentContractBinding
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContractFragment : Fragment(R.layout.fragment_contract) {

    private lateinit var binding: FragmentContractBinding
    private val dogImage: ContractFragmentArgs by navArgs()
    private val viewModel by viewModels<FavoriteDogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContractBinding.bind(view)
        drawImage()
        animateImage()
        checkedAnimation()
        setupButton()
        checkFields()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isDogDataValid.observe(viewLifecycleOwner){
            binding.tvProposal.isEnabled = it
        }
    }

    private fun checkFields() {
        binding.etName.doAfterTextChanged {

            viewModel.validateDogData()
            viewModel.validateDogName(it.toString())
        }
        binding.etDescription.doAfterTextChanged {

            viewModel.validateDogData()
            viewModel.validateDogGame(it.toString())
        }
    }

    private fun setupButton() {
        binding.tvProposal.setOnClickListener {
            val dogName = binding.etName.text.toString().trim()
            val dogGame = binding.etDescription.text.toString()
            val favorite = binding.cvFavorite.isChecked
            Snackbar.make(
                binding.root,
                getString(R.string.adoption_success_message), Snackbar.LENGTH_SHORT).show()

            adoptDog(dogName, dogGame, favorite)
            findNavController().popBackStack()
        }
    }

    private fun adoptDog(dogName: String, dogGame: String, isLoved: Boolean) {
        val dogToAdopt = DogEntity(
            imageUrl = dogImage.imgdog,
            name = dogName,
            game = dogGame,
            isFavorite = isLoved
        )
        viewModel.saveFavoriteDog(dogToAdopt)
    }

    private fun checkedAnimation() {
        binding.cvFavorite.setOnCheckedChangeListener { _, _ ->
            if (binding.cvFavorite.isChecked) {
                animateHeart()
            } else {
                stopAnimation()
            }
        }

    }


    private fun stopAnimation() {
        binding.cvFavorite.animate().apply {
            duration = 500
            scaleX(1.0F)
            scaleY(1.0F)
        }
    }

    private var animationCount = 0

    private fun animateHeart() {
        val scale = if (animationCount++ % 2 == 0) 1.5f else 1f
        binding.cvFavorite.animate().scaleX(scale).scaleY(scale).setDuration(725)
            .withEndAction(::animateHeart)
    }


    private fun drawImage() {

        binding.imgDogEntity.alpha = 0f
        binding.imgDogEntity.animate().setDuration(750).alpha(1f)

        Glide.with(requireContext())
            .load(dogImage.imgdog)
            .useAnimationPool(true)
            .transform(CenterCrop(), RoundedCorners(32))
            .into(binding.imgDogEntity)
    }

    private fun animateImage() {
        binding.imgDogEntity.setOnClickListener {
            binding.imgDogEntity.animate().apply {
                duration = 2000
                rotationY(360F)
            }.withEndAction {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.dog_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}