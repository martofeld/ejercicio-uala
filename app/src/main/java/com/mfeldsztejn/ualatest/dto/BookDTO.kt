package com.mfeldsztejn.ualatest.dto

data class BookDTO(val id: String, val nombre: String, val autor: String,
                   val disponibilidad: Boolean, val popularidad: Int, val imagen: String)