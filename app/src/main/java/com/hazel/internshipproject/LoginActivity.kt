package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hazel.internshipproject.databinding.ActivityLoginBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        checkLogin()

        viewBinder.tvSignup.setOnClickListener{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        viewBinder.btn.setOnClickListener{
            checkInputData()
        }
    }
    private fun checkInputData(){
        if(Validation.checkBlank(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.checkEmail)) &&
            Validation.checkBlank(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.checkPass)))
        { //Data not Blank
            if(Validation.validateEmail(this,viewBinder.etEmail.text.toString().trim(),resources.getString(R.string.validEmail)) &&
                Validation.validPassword(this,viewBinder.etPass.text.toString().trim(),resources.getString(R.string.validPass)))
            {  //Valid Data
                updateSharePreference()
                //findUser()
                startActivity(Intent(this@LoginActivity, HomePage::class.java))
            }
        }
    }

    private fun checkLogin(){
        val spManager = SharedPreferenceManager(this)
        val isLogged = spManager.getLogin(resources.getString(R.string.checkLogin), false)
        if(isLogged){
            startActivity(Intent(this@LoginActivity, HomePage::class.java))
        }
    }
    private fun updateSharePreference(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), true)
        spManager.saveString(resources.getString(R.string.emailTag), viewBinder.etEmail.text.toString())
    }
}