package com.duman.userposts.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.duman.userposts.data.model.UserDataUiModel
import com.duman.userposts.databinding.ItemUserBinding

private val diffCallback = object :
    DiffUtil.ItemCallback<UserDataUiModel?>() {
    override fun areItemsTheSame(oldItem: UserDataUiModel, newItem: UserDataUiModel): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UserDataUiModel, newItem: UserDataUiModel): Boolean {
        return oldItem == newItem
    }
}

class UserAdapter(val itemClick: (UserDataUiModel) -> Unit) :
    ListAdapter<UserDataUiModel, UserViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
    }
}

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserDataUiModel, itemClick: (UserDataUiModel) -> Unit) {
        binding.tvTitle.text = user.userName
        binding.tvPostCount.text = user.postList.size.toString()
        Glide.with(binding.userImage).load(user.thumbnailUrl).into(binding.userImage)
        binding.userCard.setOnClickListener {
            itemClick(user)
        }
    }
}
