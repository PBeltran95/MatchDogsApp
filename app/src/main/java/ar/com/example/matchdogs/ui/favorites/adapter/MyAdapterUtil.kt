package ar.com.example.matchdogs.ui.favorites.adapter

import androidx.recyclerview.widget.DiffUtil
import ar.com.example.matchdogs.data.models.DogEntity

class MyAdapterUtil(private val oldList: List<DogEntity>,
                    private val newList: List<DogEntity>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldList.count()


    override fun getNewListSize(): Int = newList.count()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }oldList[oldItemPosition].game != newList[newItemPosition].game -> {
                false
            }oldList[oldItemPosition].imageUrl != newList[newItemPosition].imageUrl -> {
                false
            }oldList[oldItemPosition].isFavorite != newList[newItemPosition].isFavorite -> {
                false
            }
            else -> true
        }
    }
}