package edu.neo.saludable.api


import edu.neo.saludable.api.implementacion.ApiTragoImp
import edu.neo.saludable.model.Trago
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiTrago {

    @GET("api/json/v1/1/{random}")
    fun getTrago(@Path("random") random:String): Call<Trago>


}