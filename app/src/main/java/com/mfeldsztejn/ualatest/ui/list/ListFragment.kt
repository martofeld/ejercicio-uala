package com.mfeldsztejn.ualatest.ui.list

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BooksAdapter(emptyList())
        booksRecyclerView.adapter = adapter
        viewModel.booksLiveData.observe(this, Observer {
            adapter.replaceBooks(it)
        })
        booksRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        title.setText(R.string.list_title)

        (activity as MainActivity).setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.list_fragment_menu, menu)

        menu!!.findItem(R.id.action_grid).isChecked = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_sort -> {
                viewModel.reverseSort()
                true
            }
            R.id.action_filter -> {
                viewModel.filter()
                true
            }
            R.id.action_grid -> {
                if (item.isChecked) {
                    booksRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    item.setIcon(R.drawable.ic_grid_on)
                } else {
                    booksRecyclerView.layoutManager = GridLayoutManager(context, 2)
                    item.setIcon(R.drawable.ic_grid_off)
                }
                item.isChecked = !item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
