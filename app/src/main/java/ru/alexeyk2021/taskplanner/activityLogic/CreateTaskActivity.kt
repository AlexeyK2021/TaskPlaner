package ru.alexeyk2021.taskplanner.activityLogic

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
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
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User
import ru.alexeyk2021.taskplanner.databinding.ActivityCreateTaskBinding
import java.util.*


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

        val users = mutableListOf<User>()
        var cachedUsersData = mutableListOf<DocumentSnapshot>()
        val adapter = UsersPerTaskListAdapter(users)

        usersPerTaskList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersPerTaskList.adapter = adapter

        Firebase.firestore.collection("users").get().addOnSuccessListener { result ->
            Log.d(TAG, "Caching user data")
            cachedUsersData = result.documents
//            val foundUser =
//                User(cachedUsersData.find { it.data?.get("email") == Firebase.auth.currentUser?.email }!!.data!!)
//            users.add(foundUser)
        }.addOnFailureListener {
            Log.d(TAG, "Caching failed: ${it.message.toString()}")
        }

        val cal = Calendar.getInstance()
        val todayDate =
            "${cal.get(Calendar.DAY_OF_MONTH)}.${cal.get(Calendar.MONTH)}.${cal.get(Calendar.YEAR)}"
        val tomorrowDate =
            "${cal.get(Calendar.DAY_OF_MONTH) + 1}.${cal.get(Calendar.MONTH)}.${cal.get(Calendar.YEAR)}"
        startDate.text = todayDate
        endDate.text = tomorrowDate

        startDate.setOnClickListener {
            val year = dateAndTime.get(Calendar.YEAR)
            val month = dateAndTime.get(Calendar.MONTH)
            val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val text = "$dayOfMonth.$monthOfYear.$year"
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
            val usersEmails = mutableListOf<String>()
            usersEmails.add(userEmail)
            users.forEach {
                usersEmails.add(it.email)
            }
            val task = Task(
                userEmail = userEmail,
                name = taskName.text.toString(),
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                description = taskDescription.text.toString(),
                status = 1,
                usersEmails = usersEmails
            )
            Firebase.firestore.collection("tasks").add(task.getInfo()).addOnSuccessListener {
                Log.d(TAG, "Created Task; Result: ${it.id}")
//                for (user in users) {
////                TODO("Синхронизация задачи с пользователями")
//                    user.tasksId.add(it.id)
//                    Firebase.firestore.collection("users").whereEqualTo("email", user.email).get()
//                        .addOnSuccessListener { founded_user ->
//                            founded_user.documents[0].data?.set("tasks", user.tasksId)
//                        }
//                }
                finish()
            }.addOnFailureListener {
                Log.d("Failure", it.message.toString())
            }
        }
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
    }

}