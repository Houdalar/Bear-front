package tn.esprit.baby_track.utils

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface {
    @POST("/sign-up")
    fun executeSignup(@Query("name") name:String,@Query("email") email:String,@Query("pwd") pwd:String): Call<Void?>
    companion object{
        var BASE_URL = "http://192.168.56.1:9090"
        fun create():RetrofitInterface{
            val  retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}