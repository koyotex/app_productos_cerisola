package com.example.applistadeproductos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Producto(
    @PrimaryKey val codigo: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "dsp") val dsp: Int,
    @ColumnInfo(name = "precio") val precio: Int
)