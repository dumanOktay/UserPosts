package com.duman.userposts.data.datasource

import com.duman.userposts.data.model.ApiResult
import com.duman.userposts.data.services.UserService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {

    suspend fun getUserList() = flow {
        try {
            val response = userService.getUserList()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(ApiResult.Success(body))
            } else {
                emit(ApiResult.Fail)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResult.Fail)
        }
    }

    suspend fun getPostList() = flow {
        try {
            val response = userService.getPostList()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                emit(ApiResult.Success(body))
            } else {
                emit(ApiResult.Fail)
            }
        } catch (e: Exception) {
            emit(ApiResult.Fail)
            e.printStackTrace()
        }
    }
}
