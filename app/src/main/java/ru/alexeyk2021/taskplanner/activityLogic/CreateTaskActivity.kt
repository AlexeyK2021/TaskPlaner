package ru.alexeyk2021.taskplanner.activityLogic

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import ru.alexeyk2021.taskplanner.Adapters.UsersPerTaskListAdapter
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User
import ru.alexeyk2021.taskplanner.databinding.ActivityCreateTaskBinding
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
//    private lateinit var taskName: TextView
//    private lateinit var startDate: TextView
//    private lateinit var endDate: TextView
//    private lateinit var taskDescription: TextView
//    private lateinit var dateAndTime: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateAndTime = Calendar.getInstance()
        val taskName = binding.taskName
        val startDate = binding.startDate
        val endDate = binding.endDate
        val taskDescription = binding.taskDescription
        val userFinder = binding.userFinder
        val addUser = binding.userAdd
        val save = binding.save
        val usersPerTaskList = binding.usersPerTask

        val users = mutableMapOf<String, String>()
        var cachedUsersData = mutableListOf<DocumentSnapshot>()

        usersPerTaskList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersPerTaskList.adapter = UsersPerTaskListAdapter(users)

        Firebase.firestore.collection("users").get().addOnSuccessListener {
            Log.d(TAG, "Caching user data")
            cachedUsersData = it.documents
        }.addOnFailureListener {
            Log.d(TAG, "Caching failed: ${it.message.toString()}")
        }

        startDate.setOnClickListener {
            val year = dateAndTime.get(Calendar.YEAR)
            val month = dateAndTime.get(Calendar.MONTH)
            val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val text =
                    "$dayOfMonth.$monthOfYear.$year"
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
                    "$dayOfMonth.$monthOfYear.$year"
                endDate.text = text
            }, year, month, day)
            dpd.show()
        }

        save.setOnClickListener {
            val userEmail = Firebase.auth.currentUser?.email!!.toString()
            val task = Task(
                userEmail = userEmail,
                name = taskName.text.toString(),
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                description = taskDescription.text.toString(),
                status = 0
            )
            Firebase.firestore.collection("tasks").add(task.getInfo()).addOnSuccessListener {
                Log.d(TAG, "Created Task; Result: ${it.id}")
                finish()
            }.addOnFailureListener {
                Log.d("Failure", it.message.toString())
            }
            for ((key, value) in users) {
//                TODO("Синхронизация задачи с пользователями")
            }
//
        }
        addUser.setOnClickListener {
            val userEmail = userFinder.text.toString()
            Log.d("User To Add", "Data: $userEmail")
            if (cachedUsersData.find { it.data?.get("email") == userEmail } != null) {
                users[cachedUsersData.find { it.data?.get("email") == userEmail }.toString()] =
                    userEmail
            }
            userFinder.text = Editable.Factory.getInstance().newEditable("")
        }
    }

}