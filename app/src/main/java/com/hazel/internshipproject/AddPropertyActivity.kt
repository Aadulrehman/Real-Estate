package com.hazel.internshipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityAddPropertyBinding
import com.hazel.internshipproject.databinding.ActivityHomePageBinding
import com.hazel.internshipproject.databinding.ActivityProfileBinding

class AddPropertyActivity : AppCompatActivity() {
    private lateinit var viewBinder: ActivityAddPropertyBinding
    lateinit var area:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder= ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        selectArea(viewBinder.spn)



    }
    fun selectArea(spn: Spinner) {
        val areaItems = resources.getStringArray(R.array.areaSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaItems)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spn.adapter = adapter
        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                area = areaItems[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
}
