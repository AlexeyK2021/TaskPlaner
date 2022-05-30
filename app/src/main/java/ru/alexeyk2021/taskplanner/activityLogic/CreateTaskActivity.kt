package ru.alexeyk2021.taskplanner.activityLogic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.databinding.ActivityCreateTaskBinding

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}