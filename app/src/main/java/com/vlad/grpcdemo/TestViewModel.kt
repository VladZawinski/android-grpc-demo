package com.vlad.grpcdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.launch
import message.MessageOuterClass.MessageById
import message.MessageServiceGrpc

class TestViewModel : ViewModel() {
    init {
        setUpGrpc()
    }
    private fun setUpGrpc() = viewModelScope.launch {
        val grpcClient = GrpcClient()
        val channel = grpcClient.start("10.0.2.2", 5000)
        grpcClient.createMessageStub(channel)
        grpcClient.makeACall()
    }

    override fun onCleared() {
        super.onCleared()
    }
}