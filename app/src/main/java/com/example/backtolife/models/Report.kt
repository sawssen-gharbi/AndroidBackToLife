package com.example.backtolife.models

data class Report(
    val _id: String,
    var date: String,
    var depressedMood: Int,
    var elevatedMood: Int,
    var irritabilityMood: Int,
    val mood: String,
    val symptoms: String,
    val user: String
)