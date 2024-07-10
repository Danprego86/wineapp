package com.example.wineapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Se definen todas las consultas
@Dao
interface WineDao {

    @Query("SELECT * FROM wineEntity")
    fun getAllWines(): MutableList<Wine>

    @Insert// Se devuelde el ID del vino recien agregado
    fun addWine(wine: Wine): Long

}