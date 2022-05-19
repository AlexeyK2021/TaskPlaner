package ru.alexeyk2021.taskplanner.activityLogic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.alexeyk2021.taskplanner.DbManager
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailText = binding.emailText
        val passwordText = binding.passwdText
        val usernameText = binding.username
        val registerButton = binding.registerButton
        val dbManager = DbManager.getInstance()

        registerButton.setOnClickListener {
            val loginResult = dbManager.register(
                usernameText.text.toString(),
                emailText.text.toString(),
                passwordText.text.toString()
            )
            when (loginResult) {
                DbManager.Companion.Status.USER_ALREADY_IN_DB -> makeMessage("Пользователь уже зарегистрирован")
                DbManager.Companion.Status.ALL_OK -> {
                    makeMessage("Регистрация успешна")
                }
                else -> makeMessage("Внутренняя ошибка")
            }
        }
    }

    private fun makeMessage(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}