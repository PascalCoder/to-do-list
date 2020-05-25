package com.thepascal.todolist.ui.fragments.deleted

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
import com.thepascal.todolist.ui.fragments.ScopedFragment
import com.thepascal.todolist.ui.utils.convertToToDoModelList
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DeletedFragment : ScopedFragment(), ToDoAdapter.OnTaskClickedListener {

    private val mDeletedViewModelFactory: DeletedViewModelFactory by inject()
    private val deletedViewModel: DeletedViewModel by lazy {
        ViewModelProvider(this, mDeletedViewModelFactory).get(DeletedViewModel::class.java)
    }
    private val deletedTasksLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var deletedTasksAdapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        deletedViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            deletedViewModel.getDeletedTasks().observe(viewLifecycleOwner, Observer {
                deletedTasksAdapter = ToDoAdapter(requireContext(), it.convertToToDoModelList())
                deletedTasksAdapter.setOnTaskClickedListener(this@DeletedFragment)

                val recyclerView = view.findViewById<RecyclerView?>(R.id.deletedTasksRecyclerView)
                recyclerView?.layoutManager = deletedTasksLayoutManager
                recyclerView?.adapter = deletedTasksAdapter
            })
        }
    }

    override fun onCheckBoxClicked(itemPosition: Int) {
        Log.d("Deleted Fragment", "Task deleted")
        //Should we put it back to the Active list?
    }

    override fun onDeleteImageClicked(itemPosition: Int) {
        //Remove it totally
        deletedViewModel.handleTaskDeleted(itemPosition)
        Toast.makeText(context, "Task deleted!", Toast.LENGTH_LONG).show()
    }
}
