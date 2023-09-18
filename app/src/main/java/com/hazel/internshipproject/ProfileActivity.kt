package com.hazel.internshipproject

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import com.hazel.internshipproject.databinding.ActivityLoginBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding
import com.hazel.internshipproject.databinding.ActivityProfileBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        viewBinder.etDOB.setOnClickListener{
            showDatePickerDialog()
        }
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selectedDate = dateFormat.format(calendar.time)
                viewBinder.etDOB.setText(selectedDate)

                calculateAge(year,monthOfYear,dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate=System.currentTimeMillis()-1000
        datePicker.show()
    }
    private fun calculateAge(yearOfBirth: Int,monthOfBirth: Int, dayOfBirth: Int){
        val currDate = LocalDate.now()
        val dob = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth)
        val age = Period.between(dob, currDate)
        val years = age.years
        val months = age.months
        val days = age.days
        viewBinder.tvAge.text="Your Age: ${years} years, ${months} month, ${days} days"
    }
}