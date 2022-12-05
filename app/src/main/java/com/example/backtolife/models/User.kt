package com.example.backtolife.models

data class User(
    val __v: Int,
    val _id: String,
    val address: String,
    val email: String,
    val fullName: String,
    val password: String,
    val phone: String,
    val role: String,
    val speciality: String,
    val image: String
)
data class UserReset (
    var email: String? = null,
    var resetCode : String?=null

)




data class UserResetPassword (
    var email: String? = null,
    var password: String? = null

)
data class UserResetResponse (
    val msgg: String? = null

)