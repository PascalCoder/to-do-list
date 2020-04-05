package com.thepascal.todolist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.thepascal.todolist.R
import com.thepascal.todolist.TASK_POSITION
import com.thepascal.todolist.ui.activities.ToDoActivity
import com.thepascal.todolist.model.ToDoModel

class ToDoAdapter(var context: Context, private val dataSet: List<ToDoModel>):
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_task_list, parent, false)
        return ToDoViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val toDoTask: ToDoModel = dataSet[position]
        holder.taskType.text = toDoTask.type
        holder.taskTitle.text = toDoTask.title
        holder.itemPosition = position

    }

    inner class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val taskType: TextView = itemView.findViewById(R.id.itemTaskType)
        val taskTitle: TextView = itemView.findViewById(R.id.itemTaskTitle)
        val taskCheckBox: MaterialCheckBox = itemView.findViewById(R.id.itemTaskCompleted)
        private val taskCardView: CardView = itemView.findViewById(R.id.itemTaskCardView)

        var itemPosition = 0
        init {
            taskCardView.setOnClickListener {
                val intent = Intent(context, ToDoActivity::class.java)
                intent.putExtra(TASK_POSITION, itemPosition)
                context.startActivity(intent)
            }
            
            taskCheckBox.setOnClickListener {

            }
        }
    }
}