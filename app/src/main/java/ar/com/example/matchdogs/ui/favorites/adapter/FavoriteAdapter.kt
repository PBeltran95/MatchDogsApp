package ar.com.example.matchdogs.ui.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.databinding.FavoriteItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import androidx.recyclerview.widget.DiffUtil


interface OnClickDog {
    fun onDogClick(dog: DogEntity)
    fun onLongClick(dog: DogEntity)
}

class FavoriteAdapter(private var favoriteDogs: List<DogEntity>,
                      private val onClickedDog: OnClickDog) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteDogViewHolder>(){

    private lateinit var context:Context



    inner class FavoriteDogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = FavoriteItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDogViewHolder {
        context = parent.context
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return FavoriteDogViewHolder(layoutInflater.inflate(R.layout.favorite_item, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteDogViewHolder, position: Int) {
        val dog = favoriteDogs[position]
        val dogName = dog.name
        val dogGame = dog.game
        val dogImage = dog.imageUrl
        val dogFavorite = dog.isFavorite

        drawDogInFavorites(dogName, dogGame, dogImage, dogFavorite, holder)
        deleteDog(holder, dog)
        seeDog(dog, holder)
    }

    private fun seeDog(dog: DogEntity, holder: FavoriteDogViewHolder) {
        with(holder){
            binding.root.setOnClickListener {
                onClickedDog.onDogClick(dog)
            }
        }
    }

    private fun deleteDog(holder: FavoriteDogViewHolder, dog: DogEntity) {

        with(holder){

            binding.root.setOnLongClickListener {
                onClickedDog.onLongClick(dog)
                true
            }
        }
    }


    private fun drawDogInFavorites(
        dogName: String,
        dogGame: String,
        dogImage: String,
        dogFavorite: Boolean,
        holder: FavoriteDogViewHolder
    ) {
        val circleProgressBar =  CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

        with(holder){
            Glide.with(context)
                .load(dogImage)
                .placeholder(circleProgressBar)
                .transform(CenterCrop(),RoundedCorners(32))
                .into(binding.imgDog)
            binding.tvDogName.text = dogName
            binding.tvDogGame.text = dogGame
            if (dogFavorite){
                binding.imgFavorite.visibility = View.VISIBLE
            }

        }
    }

    override fun getItemCount(): Int = favoriteDogs.size



    fun setData(newDogList: List<DogEntity>){
        val diffUtil = MyAdapterUtil(favoriteDogs, newDogList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
        favoriteDogs = newDogList
    }



}