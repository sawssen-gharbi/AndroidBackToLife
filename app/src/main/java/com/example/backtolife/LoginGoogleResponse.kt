package com.example.backtolife

import com.example.backtolife.models.User

data class LoginGoogleResponse(
    val token: String,
    val userGoogle: User
)
