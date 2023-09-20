package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }
    private fun getData()
    {
        db=AppDatabase.getInstance(this)
        GlobalScope.launch {
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
}