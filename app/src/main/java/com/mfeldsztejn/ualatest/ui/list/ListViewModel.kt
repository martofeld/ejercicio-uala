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

    companion object {
        const val ALL = 0
        const val AVAILABLE = 1
        const val UNAVAILABLE = 2
    }

    private var filterStatus = ALL
    private lateinit var books: List<Book>
    private val _booksLiveData = MutableLiveData<List<Book>>()
    private val _errorMessageLiveData = MutableLiveData<String>()

    val booksLiveData: LiveData<List<Book>>
        get() = _booksLiveData
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData
    var showAsGrid: Boolean = false

    init {
        NetworkingBus.register(this)

        BookApi
                .build()
                .books()
                .enqueue(DefaultCallback<List<BookDTO>>())
    }

    fun reverseSort() {
        AsyncTask.execute {
            // Reverse this as well
            books = books.reversed()

            val books = _booksLiveData.value!!.reversed()
            _booksLiveData.postValue(books)
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onBooksObtained(booksDTOs: List<BookDTO>) {
        books = booksDTOs
                .map { Book(it) }
                .sortedBy { -it.popularity }

        // Use post since we are on a background thread
        _booksLiveData.postValue(books)
    }

    @Subscribe
    fun onBooksFailed(apiError: ApiError) {
        _errorMessageLiveData.value = apiError.message
    }

    fun filter() {
        filterStatus++
        if (filterStatus == 3) {
            filterStatus = 0
        }
        AsyncTask.execute {
            var auxBooks: List<Book> = ArrayList(books)

            auxBooks = auxBooks.filter {
                when (filterStatus) {
                    AVAILABLE -> it.available
                    UNAVAILABLE -> !it.available
                    else -> true
                }
            }

            _booksLiveData.postValue(auxBooks)
        }
    }
}
