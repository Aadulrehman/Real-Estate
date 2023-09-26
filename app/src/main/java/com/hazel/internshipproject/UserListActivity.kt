package com.hazel.internshipproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListActivity : AppCompatActivity() {
    lateinit var db:AppDatabase
    private lateinit var recyclerView:RecyclerView
    private lateinit var dataList:ArrayList<UserData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        recyclerView=findViewById(R.id.recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList= arrayListOf<UserData>()

        getData()

        val adapter = AdapterClass(dataList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : AdapterClass.OnItemClickListener {
            override fun onItemClick(user: UserData) {
                val currentEmail = user.email
                getNumberFromDB(currentEmail)
            }
        })


    }
    private fun getData()
    {
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val myuserList: List<User> = db.userDao().getAll()
            CoroutineScope(Dispatchers.Main).launch {
                for(i in myuserList){
                    val usersData=UserData(i.name,i.email)
                    dataList.add(usersData)
                }
            }
        }
        recyclerView.adapter=AdapterClass(dataList)
    }
    private fun getNumberFromDB(currEmail:String){
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val phone= db.userDao().findPhoneThroughEmail(currEmail)
            CoroutineScope(Dispatchers.Main).launch {
                dialPhone(phone.toString())
            }
        }
    }
    private fun dialPhone(phoneNumber:String)
    {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}