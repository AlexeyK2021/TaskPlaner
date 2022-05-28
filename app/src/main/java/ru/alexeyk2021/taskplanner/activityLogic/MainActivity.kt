package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.Adapters.ChooseTaskSortingAdapter
import ru.alexeyk2021.taskplanner.Adapters.TaskRecyclerView
import ru.alexeyk2021.taskplanner.DebugActivity
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sortingTypes: RecyclerView = binding.chooseTaskSorting
        val tasksList: RecyclerView = binding.taskRecyclerView

        val buttonArray = mutableListOf<String>()
        sortingTypes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sortingTypes.adapter = ChooseTaskSortingAdapter(buttonArray)

        val tasks =
            mutableListOf<Task>(
                Task(1, 1, "TestName", "Alexey", "Today", "Tomorrow", "No", 0),
                Task(1, 1, "TestName", "Alexey", "Today", "Tomorrow", "No", 1),
                Task(1, 1, "TestName", "Alexey", "Today", "Tomorrow", "No", 2)
            )
        tasksList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tasksList.adapter = TaskRecyclerView(tasks)



        if (Firebase.auth.currentUser == null) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }
        val debugPage = Intent(this, DebugActivity::class.java)
        startActivity(debugPage)

    }
}