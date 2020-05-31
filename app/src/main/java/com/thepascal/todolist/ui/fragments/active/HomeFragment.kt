package com.thepascal.todolist.ui.fragments.active

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
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel
import com.thepascal.todolist.ui.viewmodels.utils.ToDoViewModelFactory
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : ScopedFragment(), ToDoAdapter.OnTaskClickedListener {

    private val mHomeViewModelFactory by inject<HomeViewModelFactory>()
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, mHomeViewModelFactory).get(HomeViewModel::class.java)
    }
    private val mToDoViewModelFactory by inject<ToDoViewModelFactory>()
    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider(this, mToDoViewModelFactory).get(
            ToDoViewModel::class.java
        )
    }
    private val homeLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var homeAdapter: ToDoAdapter

    private var activeTasks: List<TaskEntity> = mutableListOf()
    private var position = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        launch {
            toDoViewModel.getActiveTasks().observe(viewLifecycleOwner, Observer {
                activeTasks = it
                homeAdapter = ToDoAdapter(requireContext(), activeTasks.convertToToDoModelList())
                homeAdapter.setOnTaskClickedListener(this@HomeFragment)

                val recyclerView = root.findViewById<RecyclerView?>(R.id.homeRecyclerView)
                recyclerView?.layoutManager = homeLayoutManager
                recyclerView?.adapter = homeAdapter
            })
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            toDoViewModel.getActiveTasks().observe(viewLifecycleOwner, Observer {
                activeTasks = it
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume was called!")
    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("HomeFragment", "Clicked!")
        //val handler = Handler(Looper.getMainLooper())
        launch {

            val taskEntity = activeTasks[itemPosition]
            position = itemPosition

            if (position != -1) {
                homeViewModel.handleTaskCompleted(taskEntity)
                position = -1
            }

            Toast.makeText(requireContext(), "Task completed", Toast.LENGTH_LONG).show()
        }

        /*handler.postDelayed({
            homeAdapter.notifyDataSetChanged()
        }, 0)*/
    }

    override fun onDeleteImageClicked(itemPosition: Int) {

        launch {
            val taskEntity = activeTasks[itemPosition]
            position = itemPosition

            if (position != -1) {
                homeViewModel.handleTaskDeleted(taskEntity)
                position = -1
            }
            Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_LONG).show()
        }

    }
}
