package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.adapters.ChooseTaskSortingAdapter
import ru.alexeyk2021.taskplanner.adapters.TaskRecyclerView
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.SelectingTasks
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskSorter: SelectingTasks
    private lateinit var tasksList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_TaskPlanner)
        setContentView(binding.root)


        if (Firebase.auth.currentUser == null) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }

        taskSorter = SelectingTasks(this)
        val sortingTypes: RecyclerView = binding.chooseTaskSorting
        tasksList = binding.taskRecyclerView
        val addTask: FloatingActionButton = binding.addTask

        addTask.setOnClickListener {
            val createTaskPage = Intent(this, CreateTaskActivity::class.java)
            startActivity(createTaskPage)
//            TODO("Make an turn to CreateTask Activity and it too")
        }

        val buttonArray = mutableListOf(
            getString(R.string.task_working_list),
            getString(R.string.task_not_started_list),
            getString(R.string.task_done_list)
        )
        sortingTypes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sortingTypes.adapter = ChooseTaskSortingAdapter(buttonArray, taskSorter)

        tasksList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        updateList()
    }

    fun updateList() {
        Firebase.firestore.collection("tasks").get()
            .addOnSuccessListener { result ->
                val tasks = mutableListOf<Task>()
                for (doc in result.documents) {
                    tasks.add(Task(doc.data!!))
                }
                val relevantTasks =
                    taskSorter.generateList(tasks, Firebase.auth.currentUser?.email.toString())
                val adapter = TaskRecyclerView(relevantTasks)
                tasksList.adapter = adapter
//                adapter.notifyDataSetChanged()
                Log.d("TaskList", "Updated")
            }.addOnFailureListener { result ->
                Log.e("ReadFromDB", "Can't read tasks from DB ${result.message}")
                Toast.makeText(this, "Server is offline", Toast.LENGTH_LONG).show()
            }
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

//    override fun onResume() {
//        super.onResume()
//        if (Firebase.auth.currentUser != null) {
//            val debugPage = Intent(this, DebugActivity::class.java)
//            startActivity(debugPage)
//        }
//    }
}