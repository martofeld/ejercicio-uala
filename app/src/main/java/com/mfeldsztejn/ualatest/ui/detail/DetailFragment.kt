package com.mfeldsztejn.ualatest.ui.detail

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.ualatest.MainActivity
import com.mfeldsztejn.ualatest.R
import com.mfeldsztejn.ualatest.model.Book
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment() {

    companion object {
        const val BOOK_KEY = "BOOK"

        fun newInstance(book: Book): DetailFragment {
            val args = Bundle()
            args.putSerializable(BOOK_KEY, book)

            val fragment = DetailFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        book = arguments!!.get(BOOK_KEY) as Book

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        ViewCompat.setTransitionName(view.bookAuthor, "${book.id}_author")
        ViewCompat.setTransitionName(view.bookTitle, "${book.id}_title")
        ViewCompat.setTransitionName(view.bookImage, "${book.id}_image")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(toolbar)

        bookAuthor.text = book.author
        bookTitle.text = book.name
        bookPopularity.text = context!!.getString(R.string.popularity, book.popularity)
        bookAvailable.text = context!!.getString(
                if (book.available) R.string.available else R.string.unavailable
        )
        Glide.with(bookImage)
                .load(book.image)
                .apply(
                        RequestOptions
                                .centerCropTransform()
                                .error(R.drawable.ic_image_error)
                )
                .into(bookImage)
    }
}