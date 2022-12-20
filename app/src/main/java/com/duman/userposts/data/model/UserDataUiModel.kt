package com.duman.userposts.data.model

data class UserDataUiModel(
    val userId: Int,
    val userName: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val postList: List<Post>
)
