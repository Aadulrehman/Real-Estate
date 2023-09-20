package com.hazel.internshipproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.hazel.internshipproject.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity(){
    private lateinit var viewBinder:ActivityMainBinding
    lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        viewBinder.btn.setOnClickListener{
            checkInputData()
        }
        viewBinder.tvSignIn.setOnClickListener{
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
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
                updateSharePreference()
                updateDatabase()
            }
        }
    }
    private fun updateSharePreference(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), true)
        spManager.saveString(resources.getString(R.string.emailTag), viewBinder.etEmail.text.toString())
    }
    private fun updateDatabase(){
        db=AppDatabase.getInstance(this)
        GlobalScope.launch {
            val existsEmail= db.userDao().findByEmail(viewBinder.etEmail.text.toString().trim())
            val existsPhone = db.userDao().findByPhone(viewBinder.etPhone.text.toString().trim())
            CoroutineScope(Dispatchers.Main).launch {
                if(existsEmail!=null){
                    viewBinder.etEmail.error = "Email already exists"
                    viewBinder.etPhone.error=null
                }
                else if(existsPhone!=null){
                    viewBinder.etEmail.error=null
                    viewBinder.etPhone.error = "Phone no already exists"
                }
                else{//user is new
                    viewBinder.etEmail.error=null
                    viewBinder.etPhone.error=null
                    val user = User(
                        0,
                        viewBinder.etEmail.text.toString().trim(),
                        viewBinder.etPhone.text.toString().trim(),
                        viewBinder.etName.text.toString().trim(),
                        viewBinder.etPass.text.toString().trim())
                    db.userDao().insert(user)
                    startActivity(Intent(this@MainActivity, HomePage::class.java))
                    finish()
                }
            }
        }
    }
}
