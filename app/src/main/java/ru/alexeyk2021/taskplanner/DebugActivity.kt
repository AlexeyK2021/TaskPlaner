package ru.alexeyk2021.taskplanner

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.databinding.ActivityDebugBinding

class DebugActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDebugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebugBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val connectButton = binding.connect
        val sendButton = binding.addingTestData
        val readButton = binding.readData
        val findUser = binding.findUser
        val deleteUser = binding.deleteUser
//        val dbManager = DbManager.getInstance()
        val auth = Firebase.auth
        val db = Firebase.firestore

        connectButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                "9151243736", "12345678"
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
//        sendButton.setOnClickListener {
//            val task = Task(
//                userEmail = ,
//                userId = 1,
//                name = "Test Task",
//                authorName = "Alex",
//                startDate = "Today",
//                endDate = "tomorrow",
//            )
////            TaskManager.getInstance().addTask(task.getInfo())
//
//        }

        readButton.setOnClickListener {
//            TaskManager.getInstance().getTasks()
            Firebase.firestore.collection("tasks").get().addOnSuccessListener { result ->
            }
        }
        findUser.setOnClickListener {
//            dbManager.findUser("91251243736@mail.ru")
        }
        deleteUser.setOnClickListener {

        }
    }

}