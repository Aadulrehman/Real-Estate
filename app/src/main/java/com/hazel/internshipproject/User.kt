package com.hazel.internshipproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val phone: String,
    val name: String,
    val password: String
)
