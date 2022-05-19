package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.alexeyk2021.taskplanner.DbManager
import ru.alexeyk2021.taskplanner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton
        val registerButton = binding.registerButton

        val loginText = binding.emailText
        val passwordText = binding.passwdText
        val dbManager = DbManager.getInstance()

        loginButton.setOnClickListener {
            val loginResult = dbManager.login(
                loginText.text.toString(),
                passwordText.text.toString()
            )
            when (loginResult) {
                DbManager.Companion.Status.ALL_OK -> {
                    makeMessage("Авторизация успешна")
                    TODO("ВХОД НА СЕРВЕР")
                }
                DbManager.Companion.Status.INCORRECT_PASSWORD -> {
                    makeMessage("Авторизация неуспешна. Неверный пароль")
                }
                DbManager.Companion.Status.NO_USER_IN_DB -> {
                    makeMessage("Авторизация неуспешна. Вы не зарегистрированы")
                }
                else -> {
                    makeMessage("Авторизация неуспешна. Неполадки на сервере")
                }
            }
        }

        registerButton.setOnClickListener {
            val registerWindow = Intent(this, RegisterActivity::class.java)
            startActivity(registerWindow)
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