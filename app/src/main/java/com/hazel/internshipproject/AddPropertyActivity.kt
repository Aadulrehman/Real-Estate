package com.hazel.internshipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityAddPropertyBinding
import com.hazel.internshipproject.databinding.ActivityHomePageBinding
import com.hazel.internshipproject.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddPropertyActivity : AppCompatActivity() {
    lateinit var db:AppDatabase
    private lateinit var viewBinder: ActivityAddPropertyBinding
    private lateinit var area: String
    private lateinit var email:String
    private lateinit var purpose:String
    private lateinit var interior:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        purpose=resources.getString(R.string.SaleTag)
        interior=resources.getString(R.string.NonFurnishTag)

        selectArea(viewBinder.spn)
        viewBinder.rbNonFurnished.isChecked = true
        viewBinder.rbForSale.isChecked=true

        getEmailFromSP()
        checkFurnished()
        checkPurpose()

        viewBinder.btnAdd.setOnClickListener{
            validateProperty()
        }
    }
    private fun validateProperty(){
        if(Validation.checkBlank(this,viewBinder.etCity.text.toString(),resources.getString(R.string.emptyCity)) &&
            Validation.checkBlank(this,viewBinder.etAddress.text.toString().trim(),resources.getString(R.string.emptyAddress)) &&
            Validation.checkBlank(this,viewBinder.etRooms.text.toString().trim(),resources.getString(R.string.emptyRoom)) &&
            Validation.checkBlank(this,viewBinder.etBaths.text.toString().trim(),resources.getString(R.string.emptyBath)) &&
            Validation.checkBlank(this,viewBinder.etKitchen.text.toString().trim(),resources.getString(R.string.emptyKitchen)) &&
            Validation.checkBlank(this,viewBinder.etFloor.text.toString().trim(),resources.getString(R.string.emptyFloor))) {
            insertProperty()
            Toast.makeText(this@AddPropertyActivity,"Property Added Successfully!",Toast.LENGTH_SHORT).show()
            clearEditTexts()
        }
    }

    private fun selectArea(spn: Spinner) {
        val areaItems = resources.getStringArray(R.array.areaSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaItems)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spn.adapter = adapter
        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                area = areaItems[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    private fun checkFurnished() {
        viewBinder.InteriorGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbFurnished -> {
                    interior=resources.getString(R.string.FurnishTag)
                }
                R.id.rbNonFurnished -> {
                    interior=resources.getString(R.string.NonFurnishTag)
                }
            }
        }
    }
    private fun checkPurpose() {
        viewBinder.PurposeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbForSale -> {
                    purpose=resources.getString(R.string.SaleTag)
                }
                R.id.rbForRent -> {
                    purpose=resources.getString(R.string.RentTag)
                }
            }
        }
    }
    private fun getEmailFromSP(){
        val spManager = SharedPreferenceManager(this)
        email=spManager.getString(resources.getString(R.string.emailTag), "")
    }
    private fun insertProperty(){
        db=AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            val property = Property(0, area, viewBinder.etFloor.text.toString().trim(), viewBinder.etRooms.text.toString().trim(),
                viewBinder.etBaths.text.toString().trim(), viewBinder.etKitchen.text.toString().trim(), interior, purpose, email)
            val id=db.propertyDao().insert(property)
            val address = PropertyAddress(0, viewBinder.etAddress.text.toString(), viewBinder.etCity.text.toString(), id)
            db.propertyAddressDao().insert(address)
        }
    }
    private fun clearEditTexts(){
        viewBinder.etFloor.text.clear()
        viewBinder.etBaths.text.clear()
        viewBinder.etKitchen.text.clear()
        viewBinder.etRooms.text.clear()
        viewBinder.etAddress.text.clear()
        viewBinder.etCity.text.clear()
    }
}
