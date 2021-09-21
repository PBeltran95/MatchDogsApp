package ar.com.example.matchdogs.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.core.show
import ar.com.example.matchdogs.core.toast
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.databinding.FragmentFavoriteBinding
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModel
import ar.com.example.matchdogs.ui.favorites.adapter.FavoriteAdapter
import ar.com.example.matchdogs.ui.favorites.adapter.OnClickDog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite), OnClickDog {


    private lateinit var binding : FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteDogViewModel>()
    private val myAdapterOfFavoriteDogs by lazy { FavoriteAdapter(listOf<DogEntity>(),this@FavoriteFragment ) }


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
            binding.emptyHouse.show()
            binding.rvFavorites.hide()
            binding.rvFavorites.adapter = myAdapterOfFavoriteDogs
        }else{
            binding.emptyHouse.hide()
            binding.rvFavorites.show()
            binding.rvFavorites.adapter = myAdapterOfFavoriteDogs
            myAdapterOfFavoriteDogs.setData(it)
        }
    }

    override fun onDogClick(dog: DogEntity) {
        toast(requireContext(), "${dog.name} Clicked")
    }

    override fun onLongClick(dog: DogEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete this dog??")
            .setPositiveButton("Delete"){ _, _ ->
                viewModel.deleteDog(dog)
                recoverDogsFromDb()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}
