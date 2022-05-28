package ru.alexeyk2021.taskplanner.Adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.TaskStatus


class TaskViewOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateRange: TextView = itemView.findViewById(R.id.date_range)
    val taskStatus: TextView = itemView.findViewById(R.id.status)
    val taskName: TextView = itemView.findViewById(R.id.task_name)
    val taskAuthor: TextView = itemView.findViewById(R.id.task_author)
    val cardView: CardView = itemView.findViewById(R.id.task_cardview)
}

class TaskRecyclerView(private val tasks: List<Task>) : RecyclerView.Adapter<TaskViewOrder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewOrder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sort_tasks_button_item, parent, false)
        return TaskViewOrder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewOrder, position: Int) {
        val task = tasks[position]

        val text = task.startDate + ":" + task.endDate
        holder.dateRange.text = text
        holder.taskStatus.text = TaskStatus.values()[task.status].toString()
        holder.taskName.text = task.name
        holder.taskAuthor.text = task.authorName
        holder.cardView.setCardBackgroundColor(
            when (task.status) {
                TaskStatus.NOT_STARTED.ordinal -> Resources.getSystem()
                    .getColor(R.color.task_not_started)
                TaskStatus.WORKING.ordinal -> Resources.getSystem().getColor(R.color.task_working)
                TaskStatus.DONE.ordinal -> Resources.getSystem().getColor(R.color.task_done)
                else -> Resources.getSystem().getColor(R.color.black)
            }
        )

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}