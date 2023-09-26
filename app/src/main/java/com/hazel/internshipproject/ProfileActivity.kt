package com.hazel.internshipproject

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityLoginBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding
import com.hazel.internshipproject.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityProfileBinding
    private lateinit var email:String
    lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        getEmailFromSP()
        fetchDataFromRD()

        viewBinder.etDOB.setOnClickListener{
            showDatePickerDialog()
        }
        viewBinder.btnCalculateBMI.setOnClickListener{
            calculateBMI()
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
    private fun calculateBMI()
    {
        val weight = viewBinder.etWeight.text.toString().toFloatOrNull()
        val height = viewBinder.etHeight.text.toString().toFloatOrNull()
        if (weight != null && height != null && height > 0) {
            val bmi = weight / (height * height)
            val df = DecimalFormat("#.##")
            val formattedBMI = df.format(bmi)
            viewBinder.tvBMI.text = "Your BMI is: $formattedBMI"

        } else {
            Toast.makeText(this,resources.getString(R.string.enterField), Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchDataFromRD(){
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val name= db.userDao().findNameThroughEmail(email)
            val phone= db.userDao().findPhoneThroughEmail(email)
            CoroutineScope(Dispatchers.Main).launch {
               viewBinder.tvName.text=name
                viewBinder.tvEmail.text=email
                viewBinder.tvPhone.text=phone
            }
        }
    }
    private fun getEmailFromSP(){
        val spManager = SharedPreferenceManager(this)
        email=spManager.getString(resources.getString(R.string.emailTag), "")
    }
}