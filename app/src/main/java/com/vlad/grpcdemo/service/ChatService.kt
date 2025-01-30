package com.vlad.grpcdemo.service

import chat.Chat
import chat.Chat.Empty
import chat.ChatServiceGrpc
import chat.ChatServiceGrpc.ChatServiceStub
import com.vlad.grpcdemo.GrpcClient
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.suspendCancellableCoroutine
import stock.StockServiceGrpc
import kotlin.coroutines.resumeWithException

interface ChatService {
    suspend fun connect(
        userId: String,
        username: String
    )
    suspend fun sendMessage(
        userId: String,
        username: String,
        content: String
    )
    fun onMessageUpdated(observer: StreamObserver<Chat.MessageList>)
}
class ChatServiceImpl(
    grpcClient: GrpcClient
): ChatService {
    private val stub = ChatServiceGrpc.newStub(grpcClient.channel)
    override suspend fun connect(userId: String, username: String) {
        val chatConnection = Chat.ChatConnection.newBuilder().setUserId(userId).setUsername(username).build()
        stub.connectAsync(chatConnection)
    }

    override suspend fun sendMessage(userId: String, username: String, content: String) {
        val message = Chat.ChatMessage.newBuilder().setUserId(userId).setUsername(username).setContent(content).build()
        stub.sendMessageAsync(message)
    }

    override fun onMessageUpdated(observer: StreamObserver<Chat.MessageList>) {
        stub.onMessageUpdated(observer)
    }

}

suspend fun ChatServiceStub.sendMessageAsync(chatMessage: Chat.ChatMessage): Empty = suspendCancellableCoroutine { continuation ->
    sendMessage(chatMessage, object : StreamObserver<Empty> {
        override fun onNext(value: Empty?) {
        }

        override fun onError(t: Throwable?) {
            continuation.resumeWithException(t ?: RuntimeException("Unknown error"))
        }

        override fun onCompleted() {
            continuation.resumeWith(Result.success(Empty.newBuilder().build()))
        }
    })
}

suspend fun ChatServiceStub.connectAsync(chatConnection: Chat.ChatConnection): Empty = suspendCancellableCoroutine { continuation ->
    connect(chatConnection, object : StreamObserver<Empty> {
        override fun onNext(value: Empty?) {
        }

        override fun onError(t: Throwable?) {
            continuation.resumeWithException(t ?: RuntimeException("Unknown error"))
        }

        override fun onCompleted() {
            continuation.resumeWith(Result.success(Empty.newBuilder().build()))
        }
    })
}