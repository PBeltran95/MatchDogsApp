package ar.com.example.matchdogs.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.data.local.AppDataBase
import ar.com.example.matchdogs.data.local.LocalDogDataSource
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.data.preferences.PreferencesProvider
import ar.com.example.matchdogs.databinding.FragmentFavoriteBinding
import ar.com.example.matchdogs.domain.local.LocalDogRepoImpl
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModel
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModelFactory
import ar.com.example.matchdogs.ui.favorites.adapter.FavoriteAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding : FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteDogViewModel> { FavoriteDogViewModelFactory(LocalDogRepoImpl(
        LocalDogDataSource(AppDataBase.getDatabase(requireContext()).dogDao()))
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        recoverDogsFromDb()
    }

    private fun recoverDogsFromDb() {
        viewModel.fetchFavoriteDogs().observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
    }

    private fun initRecyclerView(it: List<DogEntity>?) {
        if (it.isNullOrEmpty()){
            val emptyList = listOf<DogEntity>()
            binding.emptyHouse.visibility = View.VISIBLE
            binding.rvFavorites.visibility = View.GONE
            binding.rvFavorites.adapter = FavoriteAdapter(emptyList)
            binding.rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        }else{
            binding.emptyHouse.visibility = View.GONE
            binding.rvFavorites.visibility = View.VISIBLE
            binding.rvFavorites.adapter = FavoriteAdapter(it!!)
            binding.rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        }

    }



}