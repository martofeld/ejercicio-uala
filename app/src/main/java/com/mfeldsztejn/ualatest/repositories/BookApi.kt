package com.mfeldsztejn.ualatest.repositories

import com.mfeldsztejn.ualatest.dto.BookDTO
import com.mfeldsztejn.ualatest.networking.RetrofitBuilder
import retrofit2.Call
import retrofit2.http.GET

interface BookApi {

    @GET("books")
    fun books(): Call<List<BookDTO>>

    companion object {
        fun build(): BookApi {
            return RetrofitBuilder
                    .build()
                    .create(BookApi::class.java)
        }
    }
}