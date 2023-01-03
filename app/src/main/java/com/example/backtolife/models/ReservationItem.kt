package com.example.backtolife.models

import java.util.*

data class ReservationItem(
    val _id: String,
    val doctor: String,
    val patient: User,
    val therapy: Therapy,
    val status: String,
    val date: Date,
)