package com.exercise.logbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exercise.logbook.adapter.EventAdapter
import com.exercise.logbook.repository.EventRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddEvent: FloatingActionButton
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        recyclerView = findViewById(R.id.recyclerViewEvents)
        fabAddEvent = findViewById(R.id.fabAddEvent)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fabAddEvent.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        eventAdapter = EventAdapter(EventRepository.getAllEvents())
        recyclerView.adapter = eventAdapter
    }
}