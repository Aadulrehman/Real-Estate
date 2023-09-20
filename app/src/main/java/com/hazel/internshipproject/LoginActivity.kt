package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hazel.internshipproject.databinding.ActivityLoginBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var db:AppDatabase
    private lateinit var viewBinder: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLogin()
        Thread.sleep(2000)
        installSplashScreen()
        viewBinder= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        viewBinder.tvSignup.setOnClickListener{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
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
                findUserFromDB()
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
    private fun findUserFromDB(){
        db=AppDatabase.getInstance(this)
        GlobalScope.launch {
            val loginAccess= db.userDao().findFromEmailToValidate(viewBinder.etEmail.text.toString().trim())
            CoroutineScope(Dispatchers.Main).launch {
                if(loginAccess==null){
                    viewBinder.etPass.error=null
                    viewBinder.etEmail.error = "Email doesn't exists"
                }
                else if(loginAccess==viewBinder.etPass.text.toString().trim()){
                    viewBinder.etEmail.error=null
                    viewBinder.etPass.error=null
                    startActivity(Intent(this@LoginActivity, HomePage::class.java))
                    finish()
                }
                else{
                    viewBinder.etEmail.error=null
                    viewBinder.etPass.error = "Password not correct"
                }
            }
        }

    }
}