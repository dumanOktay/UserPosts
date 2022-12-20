package com.duman.userposts.data.services

import com.duman.userposts.data.model.Post
import com.duman.userposts.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/SharminSirajudeen/test_resources/users")
    suspend fun getUserList(): Response<List<User>>

    @GET("/SharminSirajudeen/test_resources/posts")
    suspend fun getPostList(): Response<List<Post>>
}
