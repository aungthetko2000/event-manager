package com.exercise.logbook.model

data class Event(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val date: String,
    val location: String,
    val organiser: String,
    val type: String
)