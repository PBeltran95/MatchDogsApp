package ar.com.example.matchdogs.ui.adoptScreen

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.*
import ar.com.example.matchdogs.databinding.FragmentAdoptScreenBinding
import ar.com.example.matchdogs.presentation.adoptScreen.DogViewModel
import ar.com.example.matchdogs.ui.adoptScreen.adapters.DogAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdoptScreenFragment : Fragment(R.layout.fragment_adopt_screen), DogAdapter.OnClick{

    private lateinit var binding : FragmentAdoptScreenBinding
    private val viewModel by viewModels<DogViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdoptScreenBinding.bind(view)
        searchRandomDog()
        obtainDogs()
        onBackPressed()
    }
    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
    }


    private fun searchRandomDog() {

        binding.btnRandomSearch.setOnClickListener {
            obtainDogs()
        }

        binding.swipeRefresh.setOnRefreshListener {
            obtainDogs()

            binding.swipeRefresh.isRefreshing = false
        }

    }

    private fun obtainDogs() {
        viewModel.fetchDogs().observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.progressBar.show()
                }
                is Response.Success -> {
                    binding.progressBar.hide()
                    val puppies = it.data.imageUrl
                    initAdapter(puppies)
                }
                is Response.Failure -> {
                    toast(requireContext(), getString(R.string.error_of_calling_server, it.throwable))
                }
            }
        })
    }

    private fun initAdapter(images:List<String>) {
        binding.rvContainer.show()
        binding.rvContainer.adapter =
            DogAdapter(images, this@AdoptScreenFragment)
        binding.rvContainer.scheduleLayoutAnimation()
    }

    override fun onDogImageClick(dogImage: String) {
        val action = AdoptScreenFragmentDirections.actionAdoptScreenFragmentToContractFragment(dogImage)
        findNavController().navigate(action)
    }

}