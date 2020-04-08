package com.thepascal.todolist.ui.fragments.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepascal.todolist.R
import com.thepascal.todolist.adapter.ToDoAdapter
import com.thepascal.todolist.model.DataManager

class DeletedFragment : Fragment(), ToDoAdapter.OnTaskClickedListener {

    private val deletedViewModel: DeletedViewModel by lazy { ViewModelProvider(this).get(DeletedViewModel::class.java) }
    private val deletedTasksLayoutManager by lazy { LinearLayoutManager(context) }
    private val deletedTasksAdapter by lazy {
        val adapter = ToDoAdapter(context!!, DataManager.deletedTaskList)
        adapter.setOnTaskClickedListener(this)
        adapter
    }

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

        val recyclerView = root.findViewById<RecyclerView?>(R.id.deletedTasksRecyclerView)
        recyclerView?.layoutManager = deletedTasksLayoutManager
        recyclerView?.adapter = deletedTasksAdapter

        return root
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
