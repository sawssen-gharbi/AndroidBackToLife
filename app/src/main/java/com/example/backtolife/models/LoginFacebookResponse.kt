package com.example.backtolife

import com.example.backtolife.models.User

data class LoginFacebookResponse(
    val token: String,
    val userFacebook: User
)