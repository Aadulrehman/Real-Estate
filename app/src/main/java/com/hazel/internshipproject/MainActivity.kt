package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder= ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        checkLogin()

        viewBinder.btn.setOnClickListener{
            checkInputData()
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
}
