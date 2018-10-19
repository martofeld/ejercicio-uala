package com.mfeldsztejn.ualatest.model

import com.mfeldsztejn.ualatest.dto.BookDTO
import java.io.Serializable

class Book(bookDTO: BookDTO): Serializable {
    val id = bookDTO.id
    val author = bookDTO.autor
    val name = bookDTO.nombre
    val popularity = bookDTO.popularidad
    val available = bookDTO.disponibilidad
    val image = bookDTO.imagen
}