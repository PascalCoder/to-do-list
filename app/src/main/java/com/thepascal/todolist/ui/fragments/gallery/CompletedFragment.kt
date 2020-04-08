package com.thepascal.todolist.ui.fragments.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.todolist.R
import com.thepascal.todolist.adapter.ToDoAdapter
import com.thepascal.todolist.model.DataManager

class CompletedFragment : Fragment(), ToDoAdapter.OnTaskClickedListener {

    private val completedViewModel: CompletedViewModel by lazy {
        ViewModelProvider(this).get(CompletedViewModel::class.java)
    }
    private val completedTasksLayoutManager by lazy { LinearLayoutManager(context) }
    private val completedTasksAdapter by lazy {
        val adapter = ToDoAdapter(context!!, DataManager.completedTaskList)
        adapter.setOnTaskClickedListener(this)
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        completedViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val recyclerView = root.findViewById<RecyclerView?>(R.id.completedTasksRecyclerView)
        recyclerView?.layoutManager = completedTasksLayoutManager
        recyclerView?.adapter = completedTasksAdapter

        return root
    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("Completed Fragment", "Item was clicked!")
        completedViewModel.handleTaskCompleted(itemPosition)
    }

    override fun onDeleteImageClicked(itemPosition: Int) {
        completedViewModel.handleTaskDeleted(itemPosition)
    }
}
