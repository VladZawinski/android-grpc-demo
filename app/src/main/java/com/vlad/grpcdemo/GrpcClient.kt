package com.vlad.grpcdemo

import android.util.Log
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import message.MessageOuterClass.Message
import message.MessageOuterClass.MessageById
import message.MessageServiceGrpc
import message.MessageServiceGrpc.MessageServiceBlockingStub
import message.MessageServiceGrpc.MessageServiceStub

class GrpcClient {
    private lateinit var channel: ManagedChannel
    private lateinit var messageServiceStub: MessageServiceBlockingStub
    private lateinit var asyncMessageServiceStub: MessageServiceStub

    fun start(host: String, port: Int): ManagedChannel {
        channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build()
        return channel
    }

    fun createMessageStub(channel: ManagedChannel) {
        messageServiceStub = MessageServiceGrpc.newBlockingStub(channel)
        asyncMessageServiceStub = MessageServiceGrpc.newStub(channel)
    }

    fun makeACall() {
        val request = MessageById.newBuilder().setId(1).build()
        asyncMessageServiceStub
            .findOne(request, object : StreamObserver<Message> {
                override fun onNext(value: Message?) {
                    Log.d("GrpcClient", "Message: ${value?.name}")
                }

                override fun onError(t: Throwable?) {
                }

                override fun onCompleted() {
                    Log.d("GrpcClient", "---Completed---")
                }

            })

    }

    fun shutdown() {
        channel.shutdown()
    }
}