package com.thepascal.todolist.ui.fragments.completed

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CompletedFragment : ScopedFragment(), ToDoAdapter.OnTaskClickedListener {

    private val mCompletedViewModelFactory by inject<CompletedViewModelFactory>()
    private val completedViewModel: CompletedViewModel by lazy {
        ViewModelProvider(this, mCompletedViewModelFactory).get(CompletedViewModel::class.java)
    }
    private val completedTasksLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var completedTasksAdapter: ToDoAdapter /*by lazy {
        val adapter = ToDoAdapter(requireContext(), DataManager.completedTaskList)
        adapter.setOnTaskClickedListener(this)
        adapter
    }*/

    /*init {
        launch {

        }
    }*/

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
        /*val recyclerView = root.findViewById<RecyclerView?>(R.id.completedTasksRecyclerView)
        recyclerView?.layoutManager = completedTasksLayoutManager
        recyclerView?.adapter = completedTasksAdapter*/

        /*launch {
            completedViewModel.loadCompletedTasks()
        }*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            //completedViewModel.loadCompletedTasks()
            completedViewModel.getCompletedTasks().observe(viewLifecycleOwner, Observer {

                completedTasksAdapter = ToDoAdapter(requireContext(), it.convertToToDoModelList())
                completedTasksAdapter.setOnTaskClickedListener(this@CompletedFragment)

                val recyclerView = view.findViewById<RecyclerView?>(R.id.completedTasksRecyclerView)
                recyclerView?.layoutManager = completedTasksLayoutManager
                recyclerView?.adapter = completedTasksAdapter
            })
        }

    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("Completed Fragment", "Item was clicked!")
        completedViewModel.handleTaskCompleted(itemPosition)
    }

    override fun onDeleteImageClicked(itemPosition: Int) {
        completedViewModel.handleTaskDeleted(itemPosition)
    }
}
