package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class HomePage : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        Log.d("LifeCycle","OnCreate Act2")
        button=findViewById(R.id.btn)
        button.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle","OnStart Act2")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle","OnDestroy Act2")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle","OnPause Act2")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle","OnResume Act2")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycle","OnStop Act2")
    }
}