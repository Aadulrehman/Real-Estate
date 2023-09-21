package com.hazel.internshipproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun findByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE phone = :phone")
    suspend fun findByPhone(phone: String): User?

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT password FROM users WHERE email = :email")
    suspend fun findFromEmailToValidate(email: String): String?

    @Query("SELECT name FROM users WHERE email = :email")
    suspend fun findNameThroughEmail(email: String): String?

    @Query("SELECT phone FROM users WHERE email = :email")
    suspend fun findPhoneThroughEmail(email: String): String?

    @Query("UPDATE users SET name = :newName WHERE email = :email")
    suspend fun updateUserNameByEmail(email: String, newName: String)



}