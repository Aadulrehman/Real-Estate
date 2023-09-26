package com.hazel.internshipproject

import android.content.Intent
import android.database.DatabaseUtils
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.hazel.internshipproject.databinding.ActivityHomePageBinding
import com.hazel.internshipproject.databinding.ActivityMainBinding

class HomePage : AppCompatActivity(),View.OnClickListener {
    private lateinit var viewBinder:ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewBinder=DataBindingUtil.setContentView(this,R.layout.activity_home_page)
        viewBinder.btnLogout.setOnClickListener(this)
        viewBinder.layoutProfile.setOnClickListener(this)
        viewBinder.propertyLayout.setOnClickListener(this)
        viewBinder.layoutList.setOnClickListener(this)
        viewBinder.EditDeleteLayout.setOnClickListener(this)
        viewBinder.SearchLayout.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
//        viewBinder.btnLogout.setOnClickListener {
//            startActivity(Intent(this@HomePage,AddPropertyActivity::class.java))
//        }
        when(p0?.id){
            R.id.propertyLayout->{
                startActivity(Intent(this@HomePage,AddPropertyActivity::class.java))
            }
            R.id.layoutProfile->{
                startActivity(Intent(this@HomePage,ProfileActivity::class.java))
            }
            R.id.layoutList->{
                startActivity(Intent(this@HomePage,UserListActivity::class.java))
            }
            R.id.EditDeleteLayout->{
                startActivity(Intent(this@HomePage,EditPropertyActivity::class.java))
            }
            R.id.SearchLayout->{
                startActivity(Intent(this@HomePage,SearchPropertyActivity::class.java))
            }
            R.id.btnLogout->{
                val spManager = SharedPreferenceManager(this)
                spManager.saveLogin(resources.getString(R.string.checkLogin), false)
                startActivity(Intent(this@HomePage,LoginActivity::class.java))
                finish()
            }
        }
    }
}