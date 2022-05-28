package ru.alexeyk2021.taskplanner.activityLogic

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.alexeyk2021.taskplanner.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val loginButton = binding.loginButton
        val registerButton = binding.registerButton

        val loginText = binding.emailText
        val passwordText = binding.passwdText

        loginButton.setOnClickListener {
            if (loginText.text.toString() != "" && passwordText.text.toString() != "") {
                auth.signInWithEmailAndPassword(
                    loginText.text.toString(),
                    passwordText.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            } else {
                Toast.makeText(
                    this,
                    "Not entered email or password",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("LOGIN_ACTIVITY", "Not entered email or password")
            }
        }

        registerButton.setOnClickListener {
            val registerActivity = Intent(this, RegisterActivity::class.java)
            if (loginText.text.toString() != "") {
                registerActivity.putExtra("EMAIL", loginText.text.toString())
            }
            startActivity(registerActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) finish()
    }

    private fun makeMessage(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
        Log.d("LOGIN_ACTIVITY", text)
    }
}