package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeyk2021.taskplanner.LoginManager
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginManager = LoginManager.getInstance()

        if (!loginManager.isLogged()) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }
    }
}