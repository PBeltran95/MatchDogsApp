package ar.com.example.matchdogs.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
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

            when(it){
                is Response.Loading -> {binding.progressBar.show()}
                is Response.Success -> {
                    binding.progressBar.hide()
                    initRecyclerView(it.data)
                    }
                is Response.Failure -> {
                    binding.emptyHouse.show()
                    toast(requireContext(), getString(R.string.error_of_calling_server, it.throwable))}

            }


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
        toast(requireContext(), getString(R.string.click_on_adopted_dog, dog.name))
    }

    override fun onLongClick(dog: DogEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.alert_dialog_message))
            .setPositiveButton(getString(R.string.delete_alert_dialog_positive_button)){ _, _ ->
                viewModel.deleteDog(dog)
                recoverDogsFromDb()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

}
