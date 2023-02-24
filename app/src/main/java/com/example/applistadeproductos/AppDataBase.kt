package com.example.applistadeproductos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Producto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}