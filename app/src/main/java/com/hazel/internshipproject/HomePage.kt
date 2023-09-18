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
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewBinder=ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        if(checkLogin()) {
            getData()   //Recieve data from shared Preference
        }
        else{
            recvData() //Recieve data from intent
        }

        viewBinder.btnLogout.setOnClickListener{
            val spManager = SharedPreferenceManager(this)
            spManager.saveLogin(resources.getString(R.string.checkLogin), false)
            startActivity(Intent(this@HomePage,MainActivity::class.java))
        }
        viewBinder.tvPhone.setOnClickListener{
            dialPhone()
        }
    }
    private fun recvData(){
        val bundle = intent.extras
        if (bundle != null) {
            viewBinder.tvName.text=bundle.getString(resources.getString(R.string.nameTag))
            viewBinder.tvEmail.text=bundle.getString(resources.getString(R.string.emailTag))
            viewBinder.tvPhone.text=bundle.getString(resources.getString(R.string.phoneTag))
        }
    }
    private fun checkLogin():Boolean{
        val spManager = SharedPreferenceManager(this)
        return spManager.getLogin(resources.getString(R.string.checkLogin), false)
    }
    private fun getData(){
        val spManager = SharedPreferenceManager(this)
        viewBinder.tvName.text=spManager.getString(resources.getString(R.string.nameTag), "")
        viewBinder.tvEmail.text=spManager.getString(resources.getString(R.string.emailTag), "")
        viewBinder.tvPhone.text=spManager.getString(resources.getString(R.string.phoneTag), "")
    }
    private fun dialPhone()
    {
        val spManager = SharedPreferenceManager(this)
        val phoneNumber =spManager.getString(resources.getString(R.string.phoneTag), "")
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}