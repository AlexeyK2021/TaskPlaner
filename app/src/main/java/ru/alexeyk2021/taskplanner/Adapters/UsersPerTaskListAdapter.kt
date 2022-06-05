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

class UsersViewOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userName: AppCompatTextView = itemView.findViewById(R.id.user_name)
    val userEmail: AppCompatTextView = itemView.findViewById(R.id.user_email)
}

class UsersPerTaskListAdapter(private val users: Map<String, String>) :
    RecyclerView.Adapter<UsersViewOrder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewOrder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sort_tasks_button_item, parent, false)
        return UsersViewOrder(itemView)
    }

    override fun onBindViewHolder(holder: UsersViewOrder, position: Int) {
        val user = users.keys.toList()[position]
        val userEmail = users[user]

        holder.userName.text = user
        holder.userEmail.text = userEmail
    }

    override fun getItemCount(): Int = users.size
}