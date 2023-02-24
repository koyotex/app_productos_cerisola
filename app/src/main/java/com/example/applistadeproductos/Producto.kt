package com.example.applistadeproductos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Producto(
    @PrimaryKey val codigo: String,
    @ColumnInfo(name = "producto") val producto: String?,
    @ColumnInfo(name = "dsp") val dsp: String?,
    @ColumnInfo(name = "precio") val precio: Int?
)