package com.duman.userposts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duman.userposts.data.model.ApiResult
import com.duman.userposts.data.model.UserDataUiModel
import com.duman.userposts.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPostViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private var _currentUserDataModel: UserDataUiModel? = null
    private var _resultFlow: MutableStateFlow<ApiResult<out List<UserDataUiModel>>?> =
        MutableStateFlow(ApiResult.Loading)

    val resultFlow: StateFlow<ApiResult<out List<UserDataUiModel>>?>
        get() = _resultFlow.asStateFlow()

    fun getCurrentUserDataModel() = _currentUserDataModel

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            _resultFlow.emit(ApiResult.Loading)
            delay(3000)
            userRepository.getUserDetailList().onEach {
                _resultFlow.emit(it)
            }.launchIn(this)
        }
    }

    fun setCurrentUserDataModel(userDataUiModel: UserDataUiModel) {
        _currentUserDataModel = userDataUiModel
    }

    fun clearUserDataModel() {
        _currentUserDataModel = null
    }
}
