package com.hazel.internshipproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PropertyAddressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(propertyAddress: PropertyAddress)

    @Query("SELECT * FROM propertyAddress")
    fun getAll(): List<PropertyAddress>

    @Query("DELETE FROM propertyAddress WHERE idProperty = :idProperty")
    fun deleteById(idProperty: Long)

}