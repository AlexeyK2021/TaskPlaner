package ru.alexeyk2021.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.alexeyk2021.taskplanner.DBConnectors.PostgresConnector
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
        val findUser = binding.findUser
        val deleteUser = binding.deleteUser
//        val dbManager = DbManager.getInstance()
        val postgresConnector = PostgresConnector.getInstance()

        connectButton.setOnClickListener {
            runBlocking {
                launch {
//                    dbManager.connect()
                    postgresConnector.connect()
                }
            }
//            postgresConnector.connect()
        }
        sendButton.setOnClickListener {
            runBlocking {
                launch {
                    val user = User()
                    user.id = 1
                    user.email = "9151243736@mail.ru"
                    user.password = "kfgnsdsdifjsfsd"
                    user.name = "Alex"
//                    Log.d("SendButton", dbManager.createUser(user).toString())
                }
            }
        }

        readButton.setOnClickListener {
            var users = ""
//            dbManager.getUsers().forEach {
//                users += it.toString() + "\n"
//            }
            Log.d("Users", users)
        }
        findUser.setOnClickListener {
//            dbManager.findUser("91251243736@mail.ru")
        }
        deleteUser.setOnClickListener {
            runBlocking {
                launch {
//                    dbManager.deleteUser(dbManager.findUser("91251243736@mail.ru")!!)
                }
            }
        }
    }
}