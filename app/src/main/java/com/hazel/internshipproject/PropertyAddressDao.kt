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

    @Query("UPDATE propertyAddress SET city = :newCity WHERE idProperty = :idProperty")
    suspend fun updateCity(idProperty: Long, newCity: String)

    @Query("UPDATE propertyAddress SET address = :newAddress WHERE idProperty = :idProperty")
    suspend fun updateAddress(idProperty: Long, newAddress: String)

}