package com.vlad.grpcdemo.service

import chat.ChatServiceGrpc
import com.vlad.grpcdemo.GrpcClient
import io.grpc.stub.StreamObserver
import stock.Stock.StockList
import stock.StockServiceGrpc

interface StockService {

}

class StockServiceImpl(
    private val grpcClient: GrpcClient
): StockService {
    private val stockStub = StockServiceGrpc.newStub(grpcClient.channel)
    fun streamStock(observer: StreamObserver<StockList>) {
        stockStub.getStockUpdates(observer)
    }
}
