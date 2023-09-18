package com.hazel.internshipproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.hazel.internshipproject.databinding.ActivityHomePageBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding

class HomePage : AppCompatActivity() {
    private lateinit var viewBinder:ActivityHomePageBinding
    private lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewBinder=ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        getEmailFromSP()   //Recieve data from shared Preference
        fetchDataFromRD()

        viewBinder.btnLogout.setOnClickListener{
            logOut()
        }
        viewBinder.layoutProfile.setOnClickListener{
            startActivity(Intent(this@HomePage,ProfileActivity::class.java))
        }
        viewBinder.layoutList.setOnClickListener{

        }
    }

    private fun getEmailFromSP(){
        val spManager = SharedPreferenceManager(this)
        email=spManager.getString(resources.getString(R.string.emailTag), "")
    }
    private fun dialPhone()
    {
        val spManager = SharedPreferenceManager(this)
        val phoneNumber =spManager.getString(resources.getString(R.string.phoneTag), "")
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
    private fun fetchDataFromRD(){

    }
    private fun logOut(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), false)
        startActivity(Intent(this@HomePage,LoginActivity::class.java))
    }
}