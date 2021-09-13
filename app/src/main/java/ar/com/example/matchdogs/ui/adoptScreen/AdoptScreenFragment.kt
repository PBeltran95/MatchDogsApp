package ar.com.example.matchdogs.ui.adoptScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.RetrofitClient
import ar.com.example.matchdogs.data.remote.DogDataSource
import ar.com.example.matchdogs.databinding.FragmentAdoptScreenBinding
import ar.com.example.matchdogs.domain.remote.DogRepositoryImplements
import ar.com.example.matchdogs.presentation.DogViewModel
import ar.com.example.matchdogs.presentation.DogViewModelFactory

class AdoptScreenFragment : Fragment(R.layout.fragment_adopt_screen) {

    private lateinit var binding : FragmentAdoptScreenBinding
    private val viewModel by viewModels<DogViewModel> {
        DogViewModelFactory(DogRepositoryImplements(DogDataSource(RetrofitClient.webService))) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdoptScreenBinding.bind(view)
    }

}