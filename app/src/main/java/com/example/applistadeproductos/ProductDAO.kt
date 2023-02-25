package com.example.applistadeproductos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM producto")
    fun getAll(): List<Producto>

    @Query("SELECT * FROM producto WHERE codigo IN (:codigo)")
    fun loadAllByIds(codigo: IntArray): List<Producto>

  
    //fun findByName(first: String, last: String): Producto

    @Insert
    fun insertAll(vararg productos: Producto)

    @Delete
    fun delete(producto: Producto)
}