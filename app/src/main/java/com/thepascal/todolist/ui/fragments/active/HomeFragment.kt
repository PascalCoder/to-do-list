package com.thepascal.todolist.ui.fragments.active

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.todolist.R
import com.thepascal.todolist.adapter.ToDoAdapter
import com.thepascal.todolist.model.DataManager

class HomeFragment : Fragment(), ToDoAdapter.OnTaskClickedListener {

    private val homeViewModel: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private val homeLayoutManager by lazy { LinearLayoutManager(context) }
    private val homeAdapter by lazy {
        val adapter = ToDoAdapter(context!!, DataManager.activeTaskList)
        adapter.setOnTaskClickedListener(this)
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val recyclerView = root.findViewById<RecyclerView?>(R.id.homeRecyclerView)
        recyclerView?.layoutManager = homeLayoutManager//LinearLayoutManager(context)
        recyclerView?.adapter = homeAdapter//toDoAdapter

        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume was called!")
    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("HomeFragment", "Clicked!")
        //val handler = Handler(Looper.getMainLooper())
        homeViewModel.handleTaskCompleted(itemPosition)
        /*handler.postDelayed({
            homeAdapter.notifyDataSetChanged()
        }, 0)*/
    }

    override fun onDeleteImageClicked(itemPosition: Int) {
        homeViewModel.handleTaskDeleted(itemPosition)
    }
}
