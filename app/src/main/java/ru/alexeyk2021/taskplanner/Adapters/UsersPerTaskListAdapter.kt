package ru.alexeyk2021.taskplanner.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User

class UsersViewOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userName: AppCompatTextView = itemView.findViewById(R.id.user_name)
    val userEmail: AppCompatTextView = itemView.findViewById(R.id.user_email)
}

class UsersPerTaskListAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UsersViewOrder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewOrder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sort_tasks_button_item, parent, false)
        return UsersViewOrder(itemView)
    }

    override fun onBindViewHolder(holder: UsersViewOrder, position: Int) {
        holder.userName.text = users[position].name
        holder.userEmail.text = users[position].email
    }

    override fun getItemCount(): Int = users.size
}