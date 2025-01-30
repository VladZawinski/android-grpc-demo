package com.vlad.grpcdemo.contract

import java.util.UUID

interface UserContract {
    val userId: String
    val userName: String
    fun saveUserName(data: String)
}

class UserContractImpl : UserContract {
    private var _userId = ""
    private var _userName = ""
    override val userId: String
        get() = _userId
    override val userName: String
        get() = _userName

    override fun saveUserName(data: String) {
        _userId = generateUUID()
        _userName = data
    }
    private fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}