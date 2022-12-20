package com.duman.userposts.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duman.userposts.data.model.Post
import com.duman.userposts.databinding.ItemCommentBinding

private val diffCallback = object :
    DiffUtil.ItemCallback<Post?>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostAdapter : ListAdapter<Post, PostViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: Post) {
        binding.tvTitle.text = user.title
        binding.tvDesc.text = user.body
    }
}
