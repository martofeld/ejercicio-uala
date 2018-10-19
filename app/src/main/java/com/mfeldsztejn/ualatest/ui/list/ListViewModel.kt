package com.mfeldsztejn.ualatest.ui.list

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mfeldsztejn.ualatest.dto.BookDTO
import com.mfeldsztejn.ualatest.model.Book
import com.mfeldsztejn.ualatest.networking.ApiError
import com.mfeldsztejn.ualatest.networking.DefaultCallback
import com.mfeldsztejn.ualatest.networking.NetworkingBus
import com.mfeldsztejn.ualatest.repositories.BookApi
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ListViewModel : ViewModel() {

    private lateinit var books: List<Book>
    private val _booksLiveData = MutableLiveData<List<Book>>()
    val booksLiveData: LiveData<List<Book>>
        get() = _booksLiveData

    init {
        NetworkingBus.register(this)

        BookApi
                .build()
                .books()
                .enqueue(DefaultCallback<List<BookDTO>>())
    }

    fun reverseSort(){
        AsyncTask.execute {
            books = books.reversed()
            _booksLiveData.postValue(books)
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onBooksObtained(booksDTOs: List<BookDTO>){
        books = booksDTOs
                .map { Book(it) }
                .sortedBy { -it.popularity }

        // Use post since we are on a background thread
        _booksLiveData.postValue(books)
    }

    @Subscribe
    fun onBooksFailed(apiError: ApiError){
        Log.e("REQUEST_FAIL", "Request failed with ${apiError.statusCode} - ${apiError.message}")
    }
}
