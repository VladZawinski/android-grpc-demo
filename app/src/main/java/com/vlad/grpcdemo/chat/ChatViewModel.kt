package com.vlad.grpcdemo.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chat.Chat.MessageList
import com.vlad.grpcdemo.chat.model.MessageModel
import com.vlad.grpcdemo.contract.UserContract
import com.vlad.grpcdemo.service.ChatService
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatService: ChatService,
    private val userContract: UserContract,
): ViewModel() {
    private var _messageFlow: MutableStateFlow<List<MessageModel>> = MutableStateFlow(emptyList())
    val messageFlow: StateFlow<List<MessageModel>> = _messageFlow
    init {
        observeMessages()
    }
    private fun observeMessages() {
        chatService.onMessageUpdated(object: StreamObserver<MessageList> {
            override fun onNext(value: MessageList?) {
                viewModelScope.launch {
                    val messages = value?.messagesList?.map {
                        MessageModel(
                            id = it.userId,
                            username = it.username,
                            body = it.content,
                            isSender = it.userId == userContract.userId
                        )
                    }
                    _messageFlow.emit(messages.orEmpty())
                }
            }

            override fun onError(t: Throwable?) {

            }

            override fun onCompleted() {
            }
        })
    }
    fun sendMessage(content: String) = viewModelScope.launch {
        chatService.sendMessage(userContract.userId, userContract.userName, content)
    }
}