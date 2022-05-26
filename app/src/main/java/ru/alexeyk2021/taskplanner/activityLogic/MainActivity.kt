package ru.alexeyk2021.taskplanner.activityLogic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexeyk2021.taskplanner.Adapters.ChooseTaskSortingAdapter
import ru.alexeyk2021.taskplanner.DebugActivity
import ru.alexeyk2021.taskplanner.LoginManager
import ru.alexeyk2021.taskplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginManager: LoginManager
    private val debug = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sortingTypes: RecyclerView = binding.chooseTaskSorting
        sortingTypes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val buttonArray = mutableListOf<String>()

        sortingTypes.adapter = ChooseTaskSortingAdapter(buttonArray)

        loginManager = LoginManager.getInstance()

        if (debug) {        //DEBUG
            val debugPage = Intent(this, DebugActivity::class.java)
            startActivity(debugPage)
        } else if (!loginManager.isLogged()) {
//            val goToAuthPage = Intent(this, LoginActivity::class.java)
//            startActivity(goToAuthPage)
        }

    }
}