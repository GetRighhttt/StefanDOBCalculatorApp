package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // Creating a textview value that we will assign later, so we make it lateinit
    lateinit var tvSelectedDate: TextView
    lateinit var tvSeconds: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all views in the onCreate
        val btnDatePicker: Button = findViewById(R.id.btn_date_picker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSeconds = findViewById(R.id.tv_seconds)

        // set the Button to click and do something
        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }
    }

    // DatePickerDialog is used to pop up a dialog box with a calender.
    //It passes in four parameters
    private fun clickDatePicker() {
        // First we create an instance of Calender and get it
        val myCalendar = Calendar.getInstance()

        // Next, we instantiate year, month, day with Calender object
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Then we initialize our DatePickerDialog, and pass in the parameters
        val dpd = DatePickerDialog(this,
            {_ , selectedYear, selectedMonth, selectedDayOfMonth ->
                // create & assign value to selected date
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                // set the selected date with value we assigned above to our textview
                tvSelectedDate?.setText(selectedDate)

                // put the date in a format of english
                val sdf = SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH)
                //parsing from the string and converting format
                val theDate = sdf.parse(selectedDate)

                // Now lets calculate the selected Date in minutes with null safety
                val selectedDateInMinutes = (theDate.time) / 60000

                // Get the current date in how much time has passed in Milliseconds since 1970
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                // Now we need to calculate in in minutes with null safety
                val currentDateInMinutes = (currentDate.time) / 60000
                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                // assign our text view to update to the difference in minutes
                tvSeconds.text = differenceInMinutes.toString()
            },
            year,
            month,
            day
        )
        // Add a limit on the max date and show the datepicker
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}