package com.mfeldsztejn.ualatest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.ualatest.MainActivity
import com.mfeldsztejn.ualatest.R
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.booksLiveData.observe(this, Observer {
            booksRecyclerView.adapter = BooksAdapter(it)
        })
        booksRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        (activity as MainActivity).setSupportActionBar(toolbar)
    }
}
