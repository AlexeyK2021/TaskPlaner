package ru.alexeyk2021.taskplanner.activityLogic

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User
import ru.alexeyk2021.taskplanner.databinding.ActivityCreateTaskBinding
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var taskName: TextView
    private lateinit var startDate: TextView
    private lateinit var endDate: TextView
    private lateinit var taskDescription: TextView
    private lateinit var dateAndTime: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateAndTime = Calendar.getInstance()
        taskName = binding.taskName
        startDate = binding.startDate
        endDate = binding.endDate
        taskDescription = binding.taskDescription
        val userFinder = binding.userFinder
        val addUser = binding.userAdd
        val save = binding.save

        val users = mutableListOf<User>()

        startDate.setOnClickListener {
            val year = dateAndTime.get(Calendar.YEAR)
            val month = dateAndTime.get(Calendar.MONTH)
            val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val text =
                    "" + dayOfMonth.toString() + " " + monthOfYear.toString() + ", " + year.toString()
                startDate.text = text
            }, year, month, day)
            dpd.show()
        }

        endDate.setOnClickListener {
            val year = dateAndTime.get(Calendar.YEAR)
            val month = dateAndTime.get(Calendar.MONTH)
            val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val text =
                    "" + dayOfMonth.toString() + " " + monthOfYear.toString() + ", " + year.toString()
                endDate.text = text
            }, year, month, day)
            dpd.show()
        }

        save.setOnClickListener {
            val task = Task(
                userEmail = Firebase.auth.currentUser?.email!!.toString(),
                name = taskName.text.toString(),
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                description = taskDescription.text.toString(),
                status = 0
            )
            Firebase.firestore.collection("tasks").add(task.getInfo())
            TODO("Синхронизация задачи с пользователями прри ее создании")
        }
        addUser.setOnClickListener {
            Firebase.firestore.collection("users").whereEqualTo("email", userFinder.text.toString()).get().addOnSuccessListener {
                val user = User(it.documents[0]) // Создвание пользователя по его email
                users.add(user)
            }
        }
    }

}