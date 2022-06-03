package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.Adapters.ChooseTaskSortingAdapter
import ru.alexeyk2021.taskplanner.Adapters.TaskRecyclerView
import ru.alexeyk2021.taskplanner.DebugActivity
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.SelectingTasks
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_TaskPlanner)
        setContentView(binding.root)


        if (Firebase.auth.currentUser == null) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }

        val sortingTypes: RecyclerView = binding.chooseTaskSorting
        val tasksList: RecyclerView = binding.taskRecyclerView
        val addTask: FloatingActionButton = binding.addTask

        addTask.setOnClickListener {
            val createTaskPage = Intent(this, CreateTaskActivity::class.java)
            startActivity(createTaskPage)
//            TODO("Make an turn to CreateTask Activity and it too")
        }

        val buttonArray = mutableListOf(
            getString(R.string.task_working),
            getString(R.string.task_not_started),
            getString(R.string.task_done)
        )
//        sortingTypes.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        sortingTypes.adapter = ChooseTaskSortingAdapter(buttonArray)

        tasksList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        Firebase.firestore.collection("tasks").get()
//            .addOnSuccessListener { result ->
//                val tasks = mutableListOf<Task>()
//                for (doc in result) {
//                    tasks.add(Task(doc))
//                }
//                val relevantTasks = SelectingTasks.getInstance().generateList(tasks)
//                tasksList.adapter = TaskRecyclerView(relevantTasks,)
//            }.addOnFailureListener { result ->
//                Log.e("ReadFromDB", "Can't read tasks from DB ${result.message}")
//                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
//            }

    }

    override fun onResume() {
        super.onResume()
//        if (Firebase.auth.currentUser != null) {
//            val debugPage = Intent(this, DebugActivity::class.java)
//            startActivity(debugPage)
//        }
    }
}