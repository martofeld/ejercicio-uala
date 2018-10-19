package com.mfeldsztejn.ualatest.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.ualatest.R
import com.mfeldsztejn.ualatest.events.ShowFragmentEvent
import com.mfeldsztejn.ualatest.model.Book
import com.mfeldsztejn.ualatest.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.book_view_holder.view.*
import org.greenrobot.eventbus.EventBus

class BooksAdapter(private var books: List<Book>) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.book_view_holder, parent, false))
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    fun replaceBooks(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }
}

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(value: Book) {
        itemView.bookAuthor.text = value.author
        itemView.bookTitle.text = value.name
        itemView.bookPopularity.text = itemView.context.getString(R.string.popularity, value.popularity)
        itemView.bookAvailable.text = itemView.context.getString(
                if (value.available) R.string.available else R.string.unavailable
        )
        Glide.with(itemView.bookImage)
                .load(value.image)
                .apply(
                        RequestOptions
                                .centerCropTransform()
                                .error(R.drawable.ic_image_error)
                )
                .into(itemView.bookImage)

        ViewCompat.setTransitionName(itemView.bookAuthor, "${value.id}_author")
        ViewCompat.setTransitionName(itemView.bookTitle, "${value.id}_title")
        ViewCompat.setTransitionName(itemView.bookImage, "${value.id}_image")

        itemView.setOnClickListener {
            EventBus.getDefault().post(ShowFragmentEvent(
                    DetailFragment.newInstance(value), listOf(itemView.bookAuthor, itemView.bookTitle, itemView.bookImage)
            ))
        }
    }

}