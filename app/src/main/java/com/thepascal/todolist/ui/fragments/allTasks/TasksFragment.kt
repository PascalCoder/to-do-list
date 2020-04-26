package com.thepascal.todolist.ui.fragments.allTasks

import android.os.Bundle
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

class TasksFragment : Fragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }

    private val tasksViewModel: TasksViewModel by lazy { ViewModelProvider(this).get(TasksViewModel::class.java) }
    private val tasksLayoutManager by lazy { LinearLayoutManager(context) }
    private val tasksAdapter by lazy { ToDoAdapter(requireContext(), DataManager.allTaskList) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)
        val textView: TextView = root.findViewById(R.id.tasksTextView)
        tasksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val recyclerView = root.findViewById<RecyclerView?>(R.id.tasksRecyclerView)
        recyclerView?.layoutManager = tasksLayoutManager
        recyclerView?.adapter = tasksAdapter

        return root
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }*/

}
