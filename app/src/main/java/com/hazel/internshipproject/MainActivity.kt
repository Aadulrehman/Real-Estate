package com.hazel.internshipproject

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivityMainBinding
    var age:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        checkLogin()

        viewBinder.btn.setOnClickListener{
            checkInputData()
        }
        viewBinder.etDOB.setOnClickListener{
            showDatePickerDialog()
        }
    }
    private fun checkInputData(){
        if(Validation.checkBlank(this,viewBinder.etName.text.toString().trim(),resources.getString(R.string.checkName)) &&
            Validation.checkBlank(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.checkEmail)) &&
            Validation.checkBlank(this,viewBinder.etPhone.text.toString().trim(),resources.getString(R.string.checkPhone)) &&
            Validation.checkBlank(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.checkPass)) &&
            Validation.checkBlank(this,viewBinder.etDOB.text.toString().trim(),resources.getString(R.string.checkDOB)))
        { //Data not Blank
            if(Validation.validateName(this,viewBinder.etName.text.toString().trim(),resources.getString(R.string.validName)) &&
                Validation.validateEmail(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.validEmail)) &&
                Validation.validatePhone(this,viewBinder.etPhone.text.toString().trim(),resources.getString(R.string.validPhone)) &&
                Validation.validPassword(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.validPass)))
            {  //Valid Data
                saveData()
                sendData()
            }
        }
    }
    private fun sendData(){
        val intent =Intent(this@MainActivity,HomePage::class.java)
        val bundle = Bundle()
        bundle.putString(resources.getString(R.string.nameTag), viewBinder.etEmail.text.toString())
        bundle.putString(resources.getString(R.string.emailTag),viewBinder.etName.text.toString())
        bundle.putString(resources.getString(R.string.phoneTag), viewBinder.etPhone.text.toString())
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun checkLogin(){
        val spManager = SharedPreferenceManager(this)
        val isLogged = spManager.getLogin(resources.getString(R.string.checkLogin), false)
        if(isLogged){
            startActivity(Intent(this@MainActivity, HomePage::class.java))
        }
    }
    private fun saveData(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), true)
        spManager.saveString(resources.getString(R.string.nameTag), viewBinder.etName.text.toString())
        spManager.saveString(resources.getString(R.string.emailTag), viewBinder.etEmail.text.toString())
        spManager.saveString(resources.getString(R.string.phoneTag), viewBinder.etPhone.text.toString())
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
            //val years = age.years
            //val months = age.months
            //val days = age.days
    }
}
