package com.exercise.logbook

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exercise.logbook.model.Event
import com.exercise.logbook.repository.EventRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEventActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etLocation: EditText
    private lateinit var etOrganiser: EditText
    private lateinit var spinnerType: Spinner
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        etName = findViewById(R.id.etEventName)
        etDate = findViewById(R.id.etEventDate)
        etLocation = findViewById(R.id.etEventLocation)
        etOrganiser = findViewById(R.id.etEventOrganiser)
        spinnerType = findViewById(R.id.spinnerEventType)
        btnSave = findViewById(R.id.btnSaveEvent)

        etDate.setOnClickListener {
            showDatePickerDialog()
        }

        val eventTypes = arrayOf("Conference", "Workshop", "Seminar", "Social", "Other")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, eventTypes)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerType.adapter = adapter

        btnSave.setOnClickListener {
            validateAndSaveEvent()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    Locale.getDefault(),
                    "%d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                etDate.setText(formattedDate)
                etDate.error = null
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun validateAndSaveEvent() {
        if (!validateInputs()) return

        val newEvent = Event(
            name = etName.text.toString().trim(),
            date = etDate.text.toString().trim(),
            location = etLocation.text.toString().trim(),
            organiser = etOrganiser.text.toString().trim(),
            type = spinnerType.selectedItem.toString()
        )

        EventRepository.addEvent(newEvent)
        Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val name = etName.text.toString().trim()
        val dateStr = etDate.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val organiser = etOrganiser.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = "Event name is required"
            isValid = false
        }
        if (dateStr.isEmpty()) {
            etDate.error = "Date is required"
            isValid = false
        }
        if (location.isEmpty()) {
            etLocation.error = "Location is required"
            isValid = false
        }
        if (organiser.isEmpty()) {
            etOrganiser.error = "Organiser is required"
            isValid = false
        }

        if (!isValid) {
            etName.requestFocus()
            return false
        }

        if (!isValidDateFormat(dateStr)) {
            etDate.error = "Invalid date format. Use YYYY-MM-DD"
            etDate.requestFocus()
            return false
        }

        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.isLenient = false
            val selectedDate = dateFormat.parse(dateStr)

            val todayCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val todayDate = todayCalendar.time

            if (selectedDate != null && selectedDate.before(todayDate)) {
                etDate.error = "Events cannot be scheduled in the past!"
                etDate.requestFocus()
                return false
            }
        } catch (e: Exception) {
            etDate.error = "Invalid date format. Use YYYY-MM-DD"
            etDate.requestFocus()
            return false
        }

        return true
    }

    private fun isValidDateFormat(dateStr: String): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.isLenient = false
        return try {
            format.parse(dateStr) != null
        } catch (e: Exception) {
            false
        }
    }
}