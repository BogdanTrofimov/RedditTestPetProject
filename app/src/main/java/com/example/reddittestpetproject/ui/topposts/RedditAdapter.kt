package com.example.reddittestpetproject.ui.topposts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittestpetproject.R
import com.example.reddittestpetproject.databinding.ViewItemBinding
import com.example.reddittestpetproject.domain.Models

class RedditAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastPosition = -1

    var childrenList: List<Models.Children> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val withBinding: ViewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            RedditViewHolder.LAYOUT,
            parent,
            false
        )
        return RedditViewHolder(withBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RedditViewHolder) {
            holder.binding.image.setOnClickListener {
                onClickListener.onClick(childrenList[position])
            }
            holder.binding.also {
                it.children = childrenList[position]
            }
        }
        if (holder.adapterPosition > lastPosition) {
            val anim = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_in_row)
            holder.itemView.startAnimation(anim)
            lastPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int = childrenList.size
}

class OnClickListener(val clickListener: (children: Models.Children) -> Unit) {
    fun onClick(children: Models.Children) = clickListener(children)
}

class RedditViewHolder(val binding: ViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.view_item
    }
}
