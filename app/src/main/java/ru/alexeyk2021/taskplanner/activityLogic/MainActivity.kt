package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeyk2021.taskplanner.DebugActivity
import ru.alexeyk2021.taskplanner.LoginManager
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginManager: LoginManager
    private val debug = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginManager = LoginManager.getInstance()

        if (debug) {        //DEBUG
            val debugPage = Intent(this, DebugActivity::class.java)
            startActivity(debugPage)
        } else if (!loginManager.isLogged()) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }
    }
}