package ar.com.example.matchdogs.core

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.hide(){
    this.visibility = View.GONE
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun Fragment.toast(context: Context, text:String, length:Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, text,length).show()
}