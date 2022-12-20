package com.duman.userposts.data.repository

import com.duman.userposts.data.datasource.UserRemoteDataSource
import com.duman.userposts.data.model.ApiResult
import com.duman.userposts.data.model.UserDataUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    private suspend fun getUserList() = userRemoteDataSource.getUserList()

    private suspend fun getPostList() = userRemoteDataSource.getPostList()

    suspend fun getUserDetailList(): Flow<ApiResult<out List<UserDataUiModel>>?> {
        val userDataList = mutableListOf<UserDataUiModel>()
        var result: ApiResult<out List<UserDataUiModel>>?
        return combine(getUserList(), getPostList()) { userL, postL ->
            if (userL is ApiResult.Success && postL is ApiResult.Success) {
                userL.data.forEach { user ->
                    userDataList.add(
                        UserDataUiModel(
                            userId = user.userId,
                            userName = user.name,
                            postList = postL.data.filter { it.userId == user.userId },
                            thumbnailUrl = user.thumbnailUrl,
                            imageUrl = user.url
                        )
                    )
                }
                result = ApiResult.Success(data = userDataList)
            } else {
                result = ApiResult.Fail
            }
            result
        }
    }
}
