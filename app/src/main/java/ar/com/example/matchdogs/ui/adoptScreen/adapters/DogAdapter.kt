package ar.com.example.matchdogs.ui.adoptScreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.databinding.DogItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DogAdapter(val images:List<String>):RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        context = parent.context

        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.dog_item, parent, false))
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item:String = images[position]
        val circleProgressBar = CircularProgressDrawable(context)
        circleProgressBar.strokeWidth = 5f
        circleProgressBar.centerRadius = 30f
        circleProgressBar.start()

        with(holder){
            Glide.with(context)
                .load(item)
                .centerCrop()
                .placeholder(circleProgressBar)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(32)))
                .into(mBiding.imgDog)
        }
    }

    override fun getItemCount(): Int {
        val limit = 10
        return if (images.size > limit){
            limit
        }else images.size
    }


    inner class DogViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBiding = DogItemBinding.bind(view)
    }
}
