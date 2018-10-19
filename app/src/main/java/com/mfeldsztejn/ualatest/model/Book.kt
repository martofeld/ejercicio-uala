package com.mfeldsztejn.ualatest.model

import com.mfeldsztejn.ualatest.dto.BookDTO

class Book(bookDTO: BookDTO) {
    val id = bookDTO.id
    val author = bookDTO.autor
    val name = bookDTO.nombre
    val popularity = bookDTO.popularidad
    val available = bookDTO.disponibilidad
    val image = bookDTO.imagen
}