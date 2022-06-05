package ru.alexeyk2021.taskplanner.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        Firebase.firestore.collection("users").whereEqualTo("email", task.userEmail).get().addOnSuccessListener {
            val text = task.startDate + ":" + task.endDate
            holder.dateRange.text = text
            holder.taskStatus.text = TaskStatus.values()[task.status].toString()
            holder.taskName.text = task.name
            holder.taskAuthor.text = it.documents[0].get("email") as String
        }

//        val text = task.startDate + ":" + task.endDate
//        holder.dateRange.text = text
//        holder.taskStatus.text = TaskStatus.values()[task.status].toString()
//        holder.taskName.text = task.name
//        holder.taskAuthor.text = task.

//        holder.cardView.setCardBackgroundColor(
//            when (task.status) {
//                TaskStatus.NOT_STARTED.ordinal -> R.color.task_not_started
//                TaskStatus.WORKING.ordinal -> Resources.getSystem().getColor(R.color.task_working)
//                TaskStatus.DONE.ordinal -> Resources.getSystem().getColor(R.color.task_done)
//                else -> Resources.getSystem().getColor(R.color.black)
//            }
//        )

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

//    @ColorInt
//    fun Context.getColorFromAttr(
//        @AttrRes attrColor: Int,
//        typedValue: TypedValue = TypedValue(),
//        resolveRefs: Boolean = true
//    ): Int {
//        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
//        return typedValue.data
//    }

}