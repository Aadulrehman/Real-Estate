package com.hazel.internshipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.app.SearchManager;
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.SearchView.OnQueryTextListener;
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.internshipproject.databinding.ActivityEditPropertyBinding
import com.hazel.internshipproject.databinding.ActivitySearchPropertyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPropertyActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivitySearchPropertyBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList:MutableList<PropertyDetailsData>
    private lateinit var searchList:MutableList<PropertyDetailsData>
    lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivitySearchPropertyBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        recyclerView=viewBinder.recyclerview
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList= mutableListOf<PropertyDetailsData>()
        searchList= mutableListOf<PropertyDetailsData>()
        getData()

        val adapter = SearchPropertyAdapter(dataList)
        recyclerView.adapter = adapter

        viewBinder.searchView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                adapter.locationFilter(p0.toString())
            }

        })

        viewBinder.searchMenu.setOnClickListener{
            startActivity(Intent(this@SearchPropertyActivity, SearchMenuActivity::class.java))
        }

    }
    private fun getData()
    {
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val myPropertyList= db.propertyDao().getAll()
            val myAddressList=db.propertyAddressDao().getAll()
                for(i in myPropertyList.indices){
                    val propertyDetailsData=PropertyDetailsData(
                        myAddressList[i].idProperty,
                        myAddressList[i].city,
                        myAddressList[i].address,
                        myPropertyList[i].room,
                        myPropertyList[i].bath,
                        myPropertyList[i].kitchen,
                        myPropertyList[i].floor,
                        myPropertyList[i].area,
                        myPropertyList[i].interior,
                        myPropertyList[i].purpose)
                    dataList.add(propertyDetailsData)
                }
        }
        //recyclerView.adapter=SearchPropertyAdapter(dataList)
    }
}