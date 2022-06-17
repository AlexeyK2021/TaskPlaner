package ru.alexeyk2021.taskplanner.activityLogic

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.Adapters.UsersPerTaskListAdapter
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.TaskStatus
import ru.alexeyk2021.taskplanner.dataClasses.User
import ru.alexeyk2021.taskplanner.databinding.ActivityEditTaskBinding


class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var cachedUsersData = mutableListOf<DocumentSnapshot>()
        val task = Task(intent.getSerializableExtra("TASK") as MutableMap<String, Any?>)

        Firebase.firestore.collection("users").get().addOnSuccessListener {
            Log.d(ContentValues.TAG, "Caching user data")
            cachedUsersData = it.documents
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "Caching failed: ${it.message.toString()}")
        }

        val statusButton1 = binding.changeTaskStatus
        val taskName = binding.taskName
        val startDate = binding.startDate
        val endDate = binding.endDate
        val taskDescription = binding.taskDescription
        val userFinder = binding.userFinder
        val addUser = binding.userAdd
        val save = binding.save
        val usersPerTaskList = binding.usersPerTask
        val users = mutableListOf<User>()
        val adapter = UsersPerTaskListAdapter(users)

        taskName.text = Editable.Factory.getInstance().newEditable(task.name)
        startDate.text = Editable.Factory.getInstance().newEditable(task.startDate)
        endDate.text = Editable.Factory.getInstance().newEditable(task.endDate)
        taskDescription.text = Editable.Factory.getInstance().newEditable(task.description)

        usersPerTaskList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        task.usersEmails.forEach { user ->
            if (cachedUsersData.find { it.data?.get("email") == user } != null) {
                val foundUser =
                    User(cachedUsersData.find { it.data?.get("email") == user }!!.data!!)
                users.add(foundUser)
            }
        }
        usersPerTaskList.adapter = adapter

        addUser.setOnClickListener {
            val userEmail = userFinder.text.toString()

            Log.d("User To Add", "Data: $userEmail")
            if (cachedUsersData.find { it.data?.get("email") == userEmail } != null) {
                val foundUser =
                    User(cachedUsersData.find { it.data?.get("email") == userEmail }!!.data!!)
                users.add(foundUser)
            }
            userFinder.text = Editable.Factory.getInstance().newEditable("")
            adapter.notifyItemInserted(0)
        }

        statusButton1.setOnClickListener {
//            Toast.makeText(this, task.name, Toast.LENGTH_LONG).show()
            when (task.status) {
                TaskStatus.NOT_STARTED.ordinal -> task.status = TaskStatus.WORKING.ordinal
                TaskStatus.WORKING.ordinal -> task.status = TaskStatus.DONE.ordinal
                else -> task.status = TaskStatus.DONE.ordinal
            }
            statusButton1.text = "OK"
//            Firebase.firestore.collection("tasks").whereEqualTo("name", task.name).get()
//                .addOnSuccessListener {
//                    Firebase.firestore.collection("tasks").document(it.documents[0].id)
//                        .update(task.getInfo())
//                }
        }

        save.setOnClickListener {
            val userEmail = Firebase.auth.currentUser?.email!!.toString()
            val usersEmails = mutableListOf<String>()
            usersEmails.add(userEmail)
            users.forEach {
                usersEmails.add(it.email)
            }
            val newTask = Task(
                userEmail = userEmail,
                name = taskName.text.toString(),
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                description = taskDescription.text.toString(),
                status = task.status,
                usersEmails = usersEmails
            )
            Firebase.firestore.collection("tasks").whereEqualTo("name", task.name).get()
                .addOnSuccessListener {
                    Firebase.firestore.collection("tasks").document(it.documents[0].id)
                        .update(newTask.getInfo())
                    finish()
                }.addOnFailureListener {
                    Log.d("Failure", it.message.toString())
                }
        }

        statusButton1.text = when (task.status) {
            TaskStatus.NOT_STARTED.ordinal -> getString(R.string.task_not_started_change)
            TaskStatus.WORKING.ordinal -> getString(R.string.task_working_change)
            else -> getString(R.string.task_done_change)
        }
    }
}