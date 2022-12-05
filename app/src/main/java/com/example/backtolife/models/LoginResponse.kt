package com.example.backtolife.models

import com.example.backtolife.models.User

data class LoginResponse(
    val token: String,
    val userInfo: User
)