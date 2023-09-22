package com.hazel.internshipproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("propertyAddress")
data class PropertyAddress(
    @PrimaryKey (autoGenerate = true) val pdID:Long=0,
    val address:String,
    val city:String,
    val idProperty:Long
)
