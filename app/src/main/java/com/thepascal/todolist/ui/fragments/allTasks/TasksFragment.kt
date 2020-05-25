package com.thepascal.todolist.ui.fragments.allTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.todolist.R
import com.thepascal.todolist.adapter.ToDoAdapter
import com.thepascal.todolist.ui.fragments.ScopedFragment
import com.thepascal.todolist.ui.utils.convertToToDoModelList
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel
import com.thepascal.todolist.ui.viewmodels.utils.ToDoViewModelFactory
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TasksFragment : ScopedFragment() {

    private val mToDoViewModelFactory by inject<ToDoViewModelFactory>()
    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider(this, mToDoViewModelFactory).get(
            ToDoViewModel::class.java
        )
    }

    private val tasksViewModel: TasksViewModel by lazy {
        ViewModelProvider(this).get(TasksViewModel::class.java)
    }
    private val tasksLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var tasksAdapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        val textView: TextView = root.findViewById(R.id.tasksTextView)
        tasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {

            toDoViewModel.getAllTasks().observe(viewLifecycleOwner, Observer {
                tasksAdapter = ToDoAdapter(requireContext(), it.convertToToDoModelList())

                val recyclerView = view.findViewById<RecyclerView?>(R.id.tasksRecyclerView)
                recyclerView?.layoutManager = tasksLayoutManager
                recyclerView?.adapter = tasksAdapter
            })

        }

    }

}
