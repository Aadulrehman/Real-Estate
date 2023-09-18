package com.hazel.internshipproject

import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.time.LocalDate
import java.time.Period

object Validation {
    fun checkBlank(context: Context, input: String, error: String):Boolean {
        if(input.isNotBlank()){
            return true
        }
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        return false
    }
    fun validateName(context: Context, input: String, error: String): Boolean {
        val regex = Regex("[0-9!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+")
        if(regex.containsMatchIn(input)){
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    fun validateEmail(context: Context, input: String, error: String):Boolean{
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$"
        if(input.matches(emailRegex.toRegex())){
            return true
        }
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        return false
    }
    fun validatePhone(context: Context, input: String, error: String):Boolean{
        val regex1 = Regex("^[0-9]{11}$")
        val regex2= """^\+\d{2}\d{10}$""".toRegex()
        if(regex1.matches(input) || regex2.matches(input)){
            return true
        }
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        return false
    }
    fun validPassword(context: Context, input: String, error: String): Boolean {
        val regex = Regex("^(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}\$")
        if(regex.matches(input)){
            return true
        }
        Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        return false
    }


}