package ru.alexeyk2021.taskplanner.activityLogic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast

import ru.alexeyk2021.taskplanner.LoginManager
import ru.alexeyk2021.taskplanner.R
import ru.alexeyk2021.taskplanner.Status
import ru.alexeyk2021.taskplanner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FireBaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameText = binding.username
        val emailText = binding.emailText
        val passwordText = binding.passwdText
        val registerButton = binding.registerButton
        val goBackButton = binding.backButton
        val loginManager = LoginManager.getInstance()

        val loginDataEmail = intent.getSerializableExtra("EMAIL")
        if (loginDataEmail != null) emailText.text =
            Editable.Factory.getInstance().newEditable(loginDataEmail.toString())

        registerButton.setOnClickListener {
            val loginResult = loginManager.register(
                usernameText.text.toString(),
                emailText.text.toString(),
                passwordText.text.toString()
            )
            when (loginResult) {
                Status.USER_ALREADY_IN_DB -> makeMessage("Пользователь уже зарегистрирован")
                Status.ALL_OK -> {
                    makeMessage("Регистрация успешна")
                    finish()
                }
                else -> makeMessage("Внутренняя ошибка")
            }
        }
        goBackButton.setOnClickListener {
            finish()
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