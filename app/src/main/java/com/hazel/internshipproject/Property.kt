package com.hazel.internshipproject

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity("property")
data class Property(
    @PrimaryKey(autoGenerate = true)
    val idProperty:Long=0,
    val area: String,
    val floor: String,
    val room: String,
    val bath: String,
    val kitchen: String,
    val interior: String,
    val purpose:String,
    val email:String
)
