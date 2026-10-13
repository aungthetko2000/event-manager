package com.exercise.logbook.repository

import com.exercise.logbook.model.Event

object EventRepository {

    private val eventList = mutableListOf<Event>(
        Event(name = "Kotlin Android Workshop", date = "2026-06-15", location = "Tech Lab A", organiser = "Google Developer Group", type = "Workshop"),
        Event(name = "Tech Startup Social", date = "2026-06-20", location = "Rooftop Lounge", organiser = "Bangkok Tech", type = "Social")
    )

    fun getAllEvents(): List<Event> = eventList

    fun addEvent(event: Event) {
        eventList.add(event)
    }
}