package com.exercise.logbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exercise.logbook.R
import com.exercise.logbook.model.Event

class EventAdapter(private val events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEventName: TextView = itemView.findViewById(R.id.tvEventName)
        val tvEventType: TextView = itemView.findViewById(R.id.tvEventType)
        val tvEventDateLocation: TextView = itemView.findViewById(R.id.tvEventDateLocation)
        val tvEventOrganiser: TextView = itemView.findViewById(R.id.tvEventOrganiser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.tvEventName.text = event.name
        holder.tvEventType.text = event.type
        holder.tvEventDateLocation.text = "${event.date}  | ${event.location}"
        holder.tvEventOrganiser.text = "Organiser: ${event.organiser}"
    }

    override fun getItemCount(): Int = events.size
}