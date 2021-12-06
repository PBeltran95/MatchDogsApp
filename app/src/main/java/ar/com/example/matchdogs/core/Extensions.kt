package ar.com.example.matchdogs.core

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

fun View.hide(){
    this.visibility = View.GONE
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun Fragment.toast(context: Context, text:String, length:Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, text,length).show()
}

fun Fragment.setGlide(context: Context, image: String?, container: ImageView){
    val circleProgressBar = CircularProgressDrawable(context).apply {
        strokeWidth = 1f
        centerRadius = 30f
        start()
    }
    Glide.with(context).load(image).placeholder(circleProgressBar).into(container)
}
