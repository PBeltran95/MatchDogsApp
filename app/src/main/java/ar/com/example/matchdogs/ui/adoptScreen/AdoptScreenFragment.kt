package ar.com.example.matchdogs.ui.adoptScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.RetrofitClient
import ar.com.example.matchdogs.data.remote.DogDataSource
import ar.com.example.matchdogs.databinding.FragmentAdoptScreenBinding
import ar.com.example.matchdogs.domain.remote.DogRepositoryImplements
import ar.com.example.matchdogs.presentation.adoptScreen.DogViewModel
import ar.com.example.matchdogs.presentation.adoptScreen.DogViewModelFactory
import ar.com.example.matchdogs.ui.adoptScreen.adapters.DogAdapter

class AdoptScreenFragment : Fragment(R.layout.fragment_adopt_screen), DogAdapter.OnClick{

    private lateinit var binding : FragmentAdoptScreenBinding
    private lateinit var adapter : DogAdapter
    private val viewModel by viewModels<DogViewModel> {
        DogViewModelFactory(DogRepositoryImplements(DogDataSource(RetrofitClient.webService))) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdoptScreenBinding.bind(view)
        searchRandomDog()

    }

    private fun searchRandomDog() {

        binding.btnRandomSearch.setOnClickListener {
            obtainDogs(getDougList())
        }
    }

    private fun getDougList():String {
        val dogBreeds = resources.getStringArray(R.array.dogList)
        val listOfBreeds = listOf(*dogBreeds)

        return listOfBreeds.random().lowercase()
    }

    private fun obtainDogs(breed:String) {
        viewModel.fetchDogs(breed).observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.alertView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    initAdapter(it.data.imageUrl)
                }
                is Response.Failure -> {
                    Toast.makeText(requireContext(), "Error: ${it.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initAdapter(images:List<String>) {
        binding.rvContainer.visibility = View.VISIBLE
        adapter = DogAdapter(images, this@AdoptScreenFragment)
        binding.rvContainer.adapter = adapter
    }

    override fun onDogImageClick(dogImage: String) {
        val action = AdoptScreenFragmentDirections.actionAdoptScreenFragmentToContractFragment(dogImage)
        findNavController().navigate(action)
    }

}