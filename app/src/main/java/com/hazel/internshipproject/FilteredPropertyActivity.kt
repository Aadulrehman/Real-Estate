package com.hazel.internshipproject

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.internshipproject.databinding.ActivityFilteredPropertyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilteredPropertyActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityFilteredPropertyBinding
    private var area: String?=null
    private lateinit var purpose:String
    private var interior:String?=null
    private var room: String?=null
    private var floor:String?=null
    private var bath:String?=null
    private var kitchen: String?=null
    private var city:String?=null
    private var address:String?=null
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList:MutableList<PropertyDetailsData>
    lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityFilteredPropertyBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        dataFromIntent()

        recyclerView=viewBinder.recyclerview
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList= mutableListOf<PropertyDetailsData>()
        getData()

        val adapter = SearchPropertyAdapter(dataList)
        recyclerView.adapter = adapter

    }
    private fun dataFromIntent(){
        val receivedBundle = intent.extras
        address= receivedBundle?.getString("address")
        room= receivedBundle?.getString("room")
        floor= receivedBundle?.getString("floor")
        kitchen= receivedBundle?.getString("kitchen")
        bath= receivedBundle?.getString("bath")
        purpose= receivedBundle?.getString("purpose").toString()
        city= receivedBundle?.getString("city")
        interior= receivedBundle?.getString("interior")
        area = receivedBundle?.getString("area")
    }
    private fun getData()
    {
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val myPropertyList= db.propertyDao().filteredProperties(area, floor, room, bath, kitchen, interior, purpose)
            val myAddressList=db.propertyAddressDao().filteredAddress(city, address)
            for(i in myPropertyList){
                val matchingAddress = myAddressList.find { it.idProperty== i.idProperty}
                if(matchingAddress!=null){
                    val propertyDetailsData=PropertyDetailsData(
                    i.idProperty, matchingAddress.city, matchingAddress.address, i.room, i.bath, i.kitchen, i.floor, i.area, i.interior, i.purpose)
                    dataList.add(propertyDetailsData)
                }
            }
        }
    }
}