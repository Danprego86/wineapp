package com.example.wineapp

import retrofit2.http.GET

interface WineService {

    //Definri todos los metodos a usar para las peticiones

    @GET(Constants.PATH_WINE)
    suspend fun getRedWines():List<Wine>//devolver un listado wine
}