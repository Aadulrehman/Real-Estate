package com.hazel.internshipproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.hazel.internshipproject.databinding.ActivitySearchMenuBinding

class SearchMenuActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivitySearchMenuBinding
    private var purpose:String="Rent"
    private var interior:String? = null
    private var area: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinder=ActivitySearchMenuBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        viewBinder.btnSale.setOnClickListener { saleRentButtonColors(viewBinder.btnSale, viewBinder.btnRent, resources.getString(R.string.SaleTag))}
        viewBinder.btnRent.setOnClickListener { saleRentButtonColors(viewBinder.btnRent, viewBinder.btnSale, resources.getString(R.string.RentTag))}
        viewBinder.btn3Marla.setOnClickListener { areaButtonColors(viewBinder.btn3Marla, viewBinder.btn5Marla, viewBinder.btn7Marla, viewBinder.btn10Marla, viewBinder.btn1Kanal, "3 Marla")}
        viewBinder.btn5Marla.setOnClickListener { areaButtonColors(viewBinder.btn5Marla, viewBinder.btn3Marla, viewBinder.btn7Marla, viewBinder.btn10Marla, viewBinder.btn1Kanal, "5 Marla")}
        viewBinder.btn7Marla.setOnClickListener { areaButtonColors(viewBinder.btn7Marla, viewBinder.btn5Marla, viewBinder.btn3Marla, viewBinder.btn10Marla, viewBinder.btn1Kanal, "7 Marla")}
        viewBinder.btn10Marla.setOnClickListener { areaButtonColors(viewBinder.btn10Marla, viewBinder.btn5Marla, viewBinder.btn7Marla, viewBinder.btn3Marla, viewBinder.btn1Kanal, "10 Marla")}
        viewBinder.btn1Kanal.setOnClickListener { areaButtonColors(viewBinder.btn1Kanal, viewBinder.btn5Marla, viewBinder.btn7Marla, viewBinder.btn10Marla, viewBinder.btn3Marla, "1 Kanal")}
        viewBinder.btnFurnished.setOnClickListener { interiorButtonColors(viewBinder.btnFurnished, viewBinder.btnnonFurnished, resources.getString(R.string.FurnishTag))}
        viewBinder.btnnonFurnished.setOnClickListener { interiorButtonColors(viewBinder.btnnonFurnished, viewBinder.btnFurnished, resources.getString(R.string.NonFurnishTag))}

        viewBinder.btnSearch.setOnClickListener{
            val bundle = Bundle()
            if(viewBinder.etFloor.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.floorTag), null)
            }
            else{
                bundle.putString(resources.getString(R.string.floorTag), viewBinder.etFloor.text.toString().trim())
            }
            if(viewBinder.etCity.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.cityTag), null)
            }
            else{
                bundle.putString(resources.getString(R.string.cityTag), viewBinder.etCity.text.toString().trim())
            }
            if(viewBinder.etAddress.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.addressTag), null)
            }
            else{
                bundle.putString(resources.getString(R.string.addressTag), viewBinder.etAddress.text.toString().trim())
            }
            if(viewBinder.etRooms.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.roomTag), null)
            }
            else{
                bundle.putString(resources.getString(R.string.roomTag), viewBinder.etRooms.text.toString().trim())
            }
            if(viewBinder.etKitchen.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.kitchenTag),null)
            }
            else{
                bundle.putString(resources.getString(R.string.kitchenTag), viewBinder.etKitchen.text.toString().trim())
            }
            if(viewBinder.etBath.text.toString().isEmpty()){
                bundle.putString(resources.getString(R.string.bathTag), null)
            }
            else{
                bundle.putString(resources.getString(R.string.bathTag), viewBinder.etBath.text.toString().trim())
            }
            bundle.putString(resources.getString(R.string.areaTag),area)
            bundle.putString(resources.getString(R.string.interiorTag), interior)
            bundle.putString(resources.getString(R.string.purposeTag), purpose)
            val intent = Intent(this@SearchMenuActivity, FilteredPropertyActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
    private fun saleRentButtonColors(selectedButton: Button, deselectedButton: Button, newPurpose:String) {
        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.lightWhite))
        deselectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
        purpose=newPurpose
    }
    private fun interiorButtonColors(selectedButton: Button, deselectedButton: Button, newInterior:String) {
        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.lightWhite))
        interior=newInterior
        deselectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
    }
    private fun areaButtonColors(selectedButton: Button, deselectedButton1: Button, deselectedButton2: Button, deselectedButton3: Button, deselectedButton4: Button, newArea:String){
        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.lightWhite))
        deselectedButton1.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
        deselectedButton2.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
        deselectedButton3.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
        deselectedButton4.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey))
        area=newArea
    }
}