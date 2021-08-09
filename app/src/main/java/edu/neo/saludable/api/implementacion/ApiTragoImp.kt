package edu.neo.saludable.api.implementacion

import edu.neo.saludable.api.ApiTrago
import edu.neo.saludable.model.Trago
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTragoImp {

    private fun getRetrofit() : Retrofit{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com/")
            .build()
    }

    fun mostrarTrago(random : String) : Call<Trago> {
        return getRetrofit().create(ApiTrago::class.java).getTrago(random)
    }

}