package com.vlad.grpcdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    init {

    }
    private fun setUpGrpc() = viewModelScope.launch {
        val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build()
//        val stub =
    }
}