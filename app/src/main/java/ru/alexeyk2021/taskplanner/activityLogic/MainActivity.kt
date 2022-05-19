package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeyk2021.taskplanner.DbManager
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var authorized = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbManager: DbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbManager = DbManager.getInstance()

        if (!authorized) {
            val goToAuthPage = Intent(this, LoginActivity::class.java)
            startActivity(goToAuthPage)
        }
    }

    override fun onResume() {
        super.onResume()
        authorized = dbManager.userId > -1
    }
}