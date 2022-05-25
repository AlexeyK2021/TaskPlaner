package ru.alexeyk2021.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.alexeyk2021.taskplanner.dataClasses.User
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
        val dbManager = DbManager.getInstance()

        connectButton.setOnClickListener { dbManager.connect() }
        sendButton.setOnClickListener {
            runBlocking {
                launch {
                    Log.d(
                        "SendButton", dbManager.createUser(
                            User(
                                id = 1,
                                name = "Alex",
                                email = "91251243736@mail.ru",
                                password = "mkmkdmkvmamsmdfmamdfopepf"
                            )
                        ).toString()
                    )
                }
            }
        }

        readButton.setOnClickListener {
            var users = ""
            dbManager.getUsers().forEach {
                users += it.toString() + "\n"
            }
            Log.d("Users", users)
        }
    }
}