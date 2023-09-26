package com.hazel.internshipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.hazel.internshipproject.databinding.ActivityAddPropertyBinding
import com.hazel.internshipproject.databinding.ActivityEditPropertyBinding
import com.hazel.internshipproject.databinding.ActivityEditPropertyDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPropertyDetailsActivity : AppCompatActivity() {

    lateinit var db:AppDatabase
    private lateinit var viewBinder: ActivityEditPropertyDetailsBinding
    private  var area: String=""
    private  var newarea: String=""
    private lateinit var purpose:String
    private lateinit var interior:String
    private lateinit var newPurpose:String
    private lateinit var newInterior:String
    private lateinit var room: String
    private lateinit var floor:String
    private lateinit var bath:String
    private lateinit var kitchen: String
    private lateinit var city:String
    private lateinit var address:String
    private var id:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityEditPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        val areaItems = resources.getStringArray(R.array.areaSpinner)

        dataFromIntent()

        selectArea(viewBinder.spn)
        val selectedItem = areaItems.indexOf(area)
        viewBinder.spn.setSelection(selectedItem)
        displayData()
        checkFurnished()
        checkPurpose()


        viewBinder.btnUpdate.setOnClickListener{
            updateData()
        }
    }
    private fun displayData(){
        viewBinder.etAddress.setText(address)
        viewBinder.etCity.setText(city)
        viewBinder.etFloor.setText(floor)
        viewBinder.etBaths.setText(bath)
        viewBinder.etKitchen.setText(kitchen)
        viewBinder.etRooms.setText(room)
        if(interior==resources.getString(R.string.FurnishTag)){
            viewBinder.rbFurnished.isChecked = true
        }
        else{
            viewBinder.rbNonFurnished.isChecked = true
        }
        if(purpose==resources.getString(R.string.SaleTag)){
            viewBinder.rbForSale.isChecked = true
        }
        else{
            viewBinder.rbForRent.isChecked = true
        }
    }
    private fun checkFurnished() {
        viewBinder.InteriorGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbFurnished -> {
                    newInterior=resources.getString(R.string.FurnishTag)
                }
                R.id.rbNonFurnished -> {
                    newInterior=resources.getString(R.string.NonFurnishTag)
                }
            }
        }
    }
    private fun checkPurpose() {
        viewBinder.PurposeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbForSale -> {
                    newPurpose=resources.getString(R.string.SaleTag)
                }
                R.id.rbForRent -> {
                    newPurpose=resources.getString(R.string.RentTag)
                }
            }
        }
    }
    private fun dataFromIntent(){
        val data = intent.getSerializableExtra("EditObjectData") as PropertyDetailsData

            id=data.id
            area = data.area
            address= data.address
            room= data.rooms
            floor=data.floor
            kitchen= data.kitchen
            bath= data.bath
            purpose= data.purpose
            interior= data.interior
            city= data.city
        newPurpose=purpose
        newInterior=interior
        newarea=area
    }
    private fun selectArea(spn: Spinner) {
        val areaItems = resources.getStringArray(R.array.areaSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaItems)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spn.adapter = adapter
        spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                newarea = areaItems[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    private fun updateData(){
        var check:Boolean=false
        db= AppDatabase.getInstance(this)
        if(viewBinder.etBaths.text.toString().trim()!=bath)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateBath(id,viewBinder.etBaths.text.toString().trim())
            }
        }
        if(viewBinder.etRooms.text.toString().trim()!=room)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateRoom(id,viewBinder.etRooms.text.toString().trim())
            }

        }
        if(viewBinder.etKitchen.text.toString().trim()!=kitchen)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateKitchen(id,viewBinder.etKitchen.text.toString().trim())
            }
        }
        if(viewBinder.etFloor.text.toString().trim()!=floor)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateFloor(id,viewBinder.etFloor.text.toString().trim())
            }
        }
        if(viewBinder.etAddress.text.toString().trim()!=address)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyAddressDao().updateAddress(id,viewBinder.etAddress.text.toString().trim())
            }
        }
        if(viewBinder.etCity.text.toString().trim()!=city)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyAddressDao().updateCity(id,viewBinder.etCity.text.toString().trim())
            }
        }
        if(area!=newarea)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateArea(id,newarea)
            }
        }
        if(purpose!=newPurpose)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updatePurpose(id,newPurpose)
            }
        }
        if(interior!=newInterior)
        {
            check=true
            CoroutineScope(Dispatchers.IO).launch{
                db.propertyDao().updateInterior(id,newInterior)
            }
        }
        if(check){
            Toast.makeText(this@EditPropertyDetailsActivity,"Data Updated Successfully!",Toast.LENGTH_SHORT).show()
        }
    }
}