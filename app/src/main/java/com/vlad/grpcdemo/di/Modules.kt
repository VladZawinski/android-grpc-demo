package com.vlad.grpcdemo.di

import com.vlad.grpcdemo.GrpcClient
import com.vlad.grpcdemo.chat.ChatViewModel
import com.vlad.grpcdemo.contract.UserContract
import com.vlad.grpcdemo.contract.UserContractImpl
import com.vlad.grpcdemo.landing.LandingViewModel
import com.vlad.grpcdemo.service.ChatService
import com.vlad.grpcdemo.service.ChatServiceImpl
import com.vlad.grpcdemo.stock.StockViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val singletonModule = module {
    single { GrpcClient("10.0.2.2", 5000) }
}

val bindModule = module {
    singleOf(::ChatServiceImpl) bind ChatService::class
    singleOf(::UserContractImpl) bind UserContract::class
}

val viewModelModule = module {
    viewModel { ChatViewModel(get(), get()) }
    viewModel { LandingViewModel(get(), get()) }
    viewModel { StockViewModel() }
}