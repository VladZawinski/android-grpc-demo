package com.vlad.grpcdemo.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.grpcdemo.contract.UserContract
import com.vlad.grpcdemo.service.ChatService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LandingViewModel(
    private val userContract: UserContract,
    private val chatService: ChatService,
): ViewModel() {
    private val _events = MutableStateFlow<LandingViewModelEvent>(LandingViewModelEvent.Idle)
    val events: StateFlow<LandingViewModelEvent> = _events.asStateFlow()

    fun next(userName: String) = viewModelScope.launch {
        userContract.saveUserName(userName)
        chatService.connect(userContract.userId, userContract.userName)
        _events.tryEmit(LandingViewModelEvent.GoToChatScreen)
    }
}

sealed class LandingViewModelEvent {
    data object Idle: LandingViewModelEvent()
    data object GoToChatScreen : LandingViewModelEvent()
}