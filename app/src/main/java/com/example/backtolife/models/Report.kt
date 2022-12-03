package com.example.backtolife.models

data class Report(
    val _id: String,
    val date: String,
    val depressedMood: Int,
    val elevatedMood: Int,
    val irritabilityMood: Int,
    val mood: String,
    val symptoms: String,
    val user: String
)