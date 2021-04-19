package com.example.roomfirebasesync.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @ColumnInfo(index = true)
    @PrimaryKey(autoGenerate = true)
    var id: Int? = -1,
    var brand: String = "",
    var model: String = "",
    var engineType: String = "",
    var engineSize: Int = 0,
    var transmissionType: String = "",
    var color: String = "",
    var imgUri: String = ""
) 
