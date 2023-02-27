package com.example.applistadeproductos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDAO {
    @Query("SELECT * FROM producto")
    fun getAll(): List<Producto>

    @Query("DELETE FROM producto")
    fun deleteAll()

    @Query("SELECT * FROM producto WHERE codigo IN (:codigo)")
    fun loadAllByIds(codigo: IntArray): List<Producto>


    @Update
    fun UpdateProduct(producto: Producto)

    @Insert
    fun insertAll(productos: MutableList<Producto>)

    @Delete
    fun delete(producto: Producto)

}

