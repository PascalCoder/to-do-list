package com.thepascal.todolist.ui.fragments.completed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.todolist.R
import com.thepascal.todolist.adapter.ToDoAdapter
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.ui.fragments.ScopedFragment
import com.thepascal.todolist.ui.utils.convertToToDoModelList
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CompletedFragment : ScopedFragment(), ToDoAdapter.OnTaskClickedListener {

    private val mCompletedViewModelFactory by inject<CompletedViewModelFactory>()
    private val completedViewModel: CompletedViewModel by lazy {
        ViewModelProvider(this, mCompletedViewModelFactory).get(CompletedViewModel::class.java)
    }
    private val completedTasksLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var completedTasksAdapter: ToDoAdapter

    private var completedTasks: List<TaskEntity> = mutableListOf()
    private var position = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {

            completedViewModel.getCompletedTasks().observe(viewLifecycleOwner, Observer {

                completedTasksAdapter = ToDoAdapter(requireContext(), it.convertToToDoModelList())
                completedTasksAdapter.setOnTaskClickedListener(this@CompletedFragment)

                val recyclerView = view.findViewById<RecyclerView?>(R.id.completedTasksRecyclerView)
                recyclerView?.layoutManager = completedTasksLayoutManager
                recyclerView?.adapter = completedTasksAdapter
            })
        }

        launch {
            completedViewModel.getCompletedTasks().observe(viewLifecycleOwner, Observer {
                completedTasks = it
            })
        }

    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("Completed Fragment", "Item was clicked!")
        launch {

            val taskEntity = completedTasks[itemPosition]
            position = itemPosition

            if (position != -1) {
                completedViewModel.handleTaskReactivated(taskEntity)
                position = -1
            }

            Toast.makeText(requireContext(), "Task is Active again.", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDeleteImageClicked(itemPosition: Int) {

        launch {

            val taskEntity = completedTasks[itemPosition]
            position = itemPosition

            if (position != -1) {
                completedViewModel.handleTaskDeleted(taskEntity)
                Toast.makeText(requireContext(), "Task was deleted.", Toast.LENGTH_LONG).show()
                position = -1
            }

        }

    }
}
