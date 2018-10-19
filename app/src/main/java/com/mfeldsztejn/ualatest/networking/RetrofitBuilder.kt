package com.mfeldsztejn.ualatest.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {

        private const val BASE_URL = "https://qodyhvpf8b.execute-api.us-east-1.amazonaws.com/test/"

        fun build(): Retrofit {

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }
}