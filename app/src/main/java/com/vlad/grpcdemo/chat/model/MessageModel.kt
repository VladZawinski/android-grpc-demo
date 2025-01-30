package com.vlad.grpcdemo.chat.model

data class MessageModel(
    val id: String,
    val username: String,
    val body: String,
    val isSender: Boolean
)
