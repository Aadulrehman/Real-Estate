package com.hazel.internshipproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property):Long

    @Query("SELECT * FROM property")
    fun getAll(): List<Property>

    @Query("DELETE FROM property WHERE idProperty = :idProperty")
    fun deleteById(idProperty: Long)

    @Query("UPDATE property SET area = :newArea WHERE idProperty = :idProperty")
    suspend fun updateArea(idProperty: Long, newArea: String)

    @Query("UPDATE property SET floor = :newFloor WHERE idProperty = :idProperty")
    suspend fun updateFloor(idProperty: Long, newFloor: String)

    @Query("UPDATE property SET room = :newRoom WHERE idProperty = :idProperty")
    suspend fun updateRoom(idProperty: Long, newRoom: String)

    @Query("UPDATE property SET bath = :newBath WHERE idProperty = :idProperty")
    suspend fun updateBath(idProperty: Long, newBath: String)

    @Query("UPDATE property SET kitchen = :newKitchen WHERE idProperty = :idProperty")
    suspend fun updateKitchen(idProperty: Long, newKitchen: String)

    @Query("UPDATE property SET interior = :newInterior WHERE idProperty = :idProperty")
    suspend fun updateInterior(idProperty: Long, newInterior: String)

    @Query("UPDATE property SET purpose = :newPurpose WHERE idProperty = :idProperty")
    suspend fun updatePurpose(idProperty: Long, newPurpose: String)
}