package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.alexeyk2021.taskplanner.DbManager
import ru.alexeyk2021.taskplanner.LoginManager
import ru.alexeyk2021.taskplanner.Status
import ru.alexeyk2021.taskplanner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton
        val registerButton = binding.registerButton

        val loginText = binding.emailText
        val passwordText = binding.passwdText
        loginManager = LoginManager.getInstance()

        loginButton.setOnClickListener {
            if (loginText.text.toString() != "" && passwordText.text.toString() != "") {
                val loginResult = loginManager.login(
                    loginText.text.toString(),
                    passwordText.text.toString()
                )
                when (loginResult) {
                    Status.ALL_OK -> {
                        makeMessage("Авторизация успешна")
                        TODO("ВХОД НА СЕРВЕР")
                    }
                    Status.INCORRECT_PASSWORD -> {
                        makeMessage("Авторизация неуспешна. Неверный пароль")
                    }
                    Status.NO_USER_IN_DB -> {
                        makeMessage("Авторизация неуспешна. Вы не зарегистрированы")
                    }
                    else -> {
                        makeMessage("Авторизация неуспешна. Неполадки на сервере")
                    }
                }
            } else makeMessage("Не введен email или пароль")
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
        if (loginManager.isLogged()) finish()
    }

    private fun makeMessage(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}