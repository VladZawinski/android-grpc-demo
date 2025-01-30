package com.vlad.grpcdemo
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class GrpcClient(
    private val host: String,
    private val port: Int
) {
    private lateinit var _channel: ManagedChannel
    val channel get() = _channel
    fun init() {
        start()
    }
    private fun start(): ManagedChannel {
        _channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build()
        return channel
    }
    fun shutdown() {
        channel.shutdown()
    }
}