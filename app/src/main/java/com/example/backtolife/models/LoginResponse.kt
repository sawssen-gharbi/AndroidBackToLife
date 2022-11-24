package com.example.backtolife.models

data class LoginResponse(
    val token: String,
    val userInfo: User
)