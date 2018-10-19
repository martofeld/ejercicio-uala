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

class BooksAdapter(val books: List<Book>) : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_BOOK = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TITLE -> TitleViewHolder(inflater.inflate(R.layout.list_title, parent, false))
            else -> BookViewHolder(inflater.inflate(R.layout.book_view_holder, parent, false))
        }
    }

    override fun getItemCount() = books.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_TITLE
            else -> VIEW_TYPE_BOOK
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            (holder as TitleViewHolder).bind(R.string.list_title)
        } else {
            (holder as BookViewHolder).bind(books[position - 1])
        }
    }
}

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class TitleViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(@StringRes text: Int) {
        (itemView as TextView).setText(text)
    }
}

class BookViewHolder(itemView: View) : ViewHolder(itemView) {

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