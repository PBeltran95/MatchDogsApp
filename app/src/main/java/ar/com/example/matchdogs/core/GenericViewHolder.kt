package ar.com.example.matchdogs.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder<T>(itemView:View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(adapter:T)
}