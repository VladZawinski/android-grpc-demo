package com.vlad.grpcdemo.service

import com.vlad.grpcdemo.GrpcClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import stock.Stock.StockList
import stock.StockServiceGrpcKt

interface StockService {
    val stockFlow: Flow<StockList>
}

class StockServiceImpl(
    grpcClient: GrpcClient
): StockService {
    private val stockStub = StockServiceGrpcKt.StockServiceCoroutineStub(grpcClient.channel)
    override val stockFlow: Flow<StockList> = stockStub.getStockUpdates(flowOf())
}
