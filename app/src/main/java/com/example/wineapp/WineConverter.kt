package com.example.wineapp

import androidx.room.TypeConverter
import com.google.gson.Gson

class WineConverter {
    @TypeConverter// en string se convertirá en un json y luego se convierte en un objeto kotlin
    fun fromJsonString(value: String?): Rating?{
        return value?.let {
            Gson().fromJson(it, Rating::class.java)
        }
    }
    @TypeConverter/// este objeto se transforma en un json y luego se transforma en un string
    fun fromRating(value: Rating?): String?{
        return value?.let {
            Gson().toJson(it)
        }
    }
}