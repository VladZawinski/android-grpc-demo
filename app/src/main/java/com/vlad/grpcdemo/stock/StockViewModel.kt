package com.vlad.grpcdemo.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad.grpcdemo.service.StockService
import com.vlad.grpcdemo.stock.model.StockModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StockViewModel(
    private val stockService: StockService
): ViewModel() {
    private val _stockFlow: MutableStateFlow<List<StockModel>> = MutableStateFlow(emptyList())
    val stockFlow: StateFlow<List<StockModel>> = _stockFlow
    init {
        stream()
    }
    private fun stream() = viewModelScope.launch {
        stockService.stockFlow.collectLatest {
            _stockFlow.value = it.stockList.map { stock ->
                StockModel(stock.symbol, stock.price.toDouble())
            }
        }
    }
}