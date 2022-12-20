package com.duman.userposts.data.model

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    object Fail : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}
