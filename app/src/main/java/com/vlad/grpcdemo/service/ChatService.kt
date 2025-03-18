package com.vlad.grpcdemo.service

import chat.Chat
import chat.Chat.Empty
import chat.Chat.MessageList
import chat.ChatServiceGrpc
import chat.ChatServiceGrpcKt
import chat.chatConnection
import chat.chatMessage
import com.vlad.grpcdemo.GrpcClient
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ChatService {
    val messagesFlow: Flow<MessageList>
    suspend fun connect(
        userId: String,
        username: String
    )
    suspend fun sendMessage(
        userId: String,
        username: String,
        content: String
    )
}
class ChatServiceImpl(
    grpcClient: GrpcClient
): ChatService {
    private val stub = ChatServiceGrpcKt.ChatServiceCoroutineStub(grpcClient.channel)
    private val javaStub = ChatServiceGrpc.newStub(grpcClient.channel)
    override val messagesFlow: Flow<MessageList> = stub.onMessageUpdated(flowOf())

    override suspend fun connect(userId: String, username: String) {
        val reque = Chat.ChatConnection.newBuilder().setUserId("1").setUsername("u").build()
        val request = chatConnection(
            block = {
                this.username = username
                this.userId = userId
            }
        )
        stub.connect(request)
    }

    override suspend fun sendMessage(userId: String, username: String, content: String) {
        val request = chatMessage {
            this.username = username
            this.userId = userId
            this.content = content
        }
        stub.sendMessage(request)
    }
}
