package com.mfeldsztejn.ualatest.ui.list

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mfeldsztejn.ualatest.MainActivity
import com.mfeldsztejn.ualatest.R
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: BooksAdapter

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

        progressBar.visibility = View.VISIBLE

        viewModel.booksLiveData.observe(this, Observer {
            progressBar.visibility = View.GONE
            adapter.replaceBooks(it)
        })
        if (viewModel.showAsGrid){
            booksRecyclerView.layoutManager = GridLayoutManager(context, 2)
            adapter = GridBooksAdapter(emptyList())
        } else {
            booksRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = LinearBooksAdapter(emptyList())
        }
        booksRecyclerView.adapter = adapter

        title.setText(R.string.list_title)

        (activity as MainActivity).setSupportActionBar(toolbar)

        viewModel.errorMessageLiveData.observe(this, Observer {
            progressBar.visibility = View.GONE
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.list_fragment_menu, menu)

        menu!!.findItem(R.id.action_grid).isChecked = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_sort -> {
                progressBar.visibility = View.VISIBLE
                viewModel.reverseSort()
                true
            }
            R.id.action_filter -> {
                progressBar.visibility = View.VISIBLE
                viewModel.filter()
                true
            }
            R.id.action_grid -> {
                item.isChecked = !item.isChecked
                viewModel.showAsGrid = item.isChecked
                if (item.isChecked) {
                    booksRecyclerView.layoutManager = GridLayoutManager(context, 2)
                    adapter = GridBooksAdapter(adapter.books)
                    item.setIcon(R.drawable.ic_grid_on)
                } else {
                    booksRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = LinearBooksAdapter(adapter.books)
                    item.setIcon(R.drawable.ic_grid_off)
                }
                booksRecyclerView.adapter = adapter
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
