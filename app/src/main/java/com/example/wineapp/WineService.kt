package com.example.wineapp

import com.example.wineapp.Constants.PATH_WINE
import retrofit2.http.GET

interface WineService {

    //Definri todos los metodos a usar para las peticiones
    @GET(PATH_WINE)
    suspend fun getRedWines():List<Wine>//devolver un listado wine
}