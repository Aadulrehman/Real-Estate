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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        viewBinder.btn.setOnClickListener{
            checkInputData()
        }
        viewBinder.tvSignIn.setOnClickListener{
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
    private fun checkInputData(){
        if(Validation.checkBlank(this,viewBinder.etName.text.toString().trim(),resources.getString(R.string.checkName)) &&
            Validation.checkBlank(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.checkEmail)) &&
            Validation.checkBlank(this,viewBinder.etPhone.text.toString().trim(),resources.getString(R.string.checkPhone)) &&
            Validation.checkBlank(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.checkPass)))
        { //Data not Blank
            if(Validation.validateName(this,viewBinder.etName.text.toString().trim(),resources.getString(R.string.validName)) &&
                Validation.validateEmail(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.validEmail)) &&
                Validation.validatePhone(this,viewBinder.etPhone.text.toString().trim(),resources.getString(R.string.validPhone)) &&
                Validation.validPassword(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.validPass)))
            {  //Valid Data
                //checkUserExistance()
                updateSharePreference()
                updateDatabase()
                startActivity(Intent(this@MainActivity, HomePage::class.java))
            }
        }
    }
    private fun updateSharePreference(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), true)
        spManager.saveString(resources.getString(R.string.emailTag), viewBinder.etEmail.text.toString())
    }
    private fun updateDatabase(){
        //update database here with email pass name and phone
    }
}
