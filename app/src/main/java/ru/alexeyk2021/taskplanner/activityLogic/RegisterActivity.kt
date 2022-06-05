package ru.alexeyk2021.taskplanner.activityLogic

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User

import ru.alexeyk2021.taskplanner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val usernameText = binding.username
        val emailText = binding.emailText
        val passwordText = binding.passwdText
        val registerButton = binding.registerButton
        val goBackButton = binding.backButton

        val loginDataEmail = intent.getSerializableExtra("EMAIL")
        if (loginDataEmail != null) emailText.text =
            Editable.Factory.getInstance().newEditable(loginDataEmail.toString())

        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                emailText.text.toString(),
                passwordText.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = User(
                            email = emailText.text.toString(),
                            name = usernameText.text.toString()
                        )

                        Firebase.firestore.collection("users").add(user.getInfo())
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
        }
        goBackButton.setOnClickListener {
            finish()
        }
    }
}