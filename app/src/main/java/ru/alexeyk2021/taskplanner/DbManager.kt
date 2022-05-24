package ru.alexeyk2021.taskplanner

import android.util.Log
import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Sorts.ascending
import ru.alexeyk2021.taskplanner.dataClasses.Task
import ru.alexeyk2021.taskplanner.dataClasses.User
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class DbManager {
    companion object {
        private var dbManager: DbManager? = null
        fun getInstance(): DbManager {
            if (dbManager == null)
                dbManager = DbManager()
            return dbManager!!
        }
    }
//    val realmSync by lazy {
//        App(AppConfiguration.Builder(BuildConfig.RealmAppId).build())
//    }

    private var databaseName: String = "myDB"
    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase

    fun findUser(email: String): User? {            // TODO:  поиск клиента в БД по email и возврат его User объекта
        val users = mongoDatabase.getCollection("users").find(eq("email", email)).toMutableList()
        if (users.isEmpty()) return null
        return User(users[0])
    }

    fun createUser(user: User): Status {
        val userInfo = user.toDocument()
        mongoDatabase.getCollection("users").insertOne(userInfo)
        return Status.ALL_OK
    }


    fun getTasks(): List<Task> {             //todo: получение задачи с сервера
        val tasks = mutableListOf<Task>()
        mongoDatabase.getCollection("tasks")
            .find(
                eq(
                    "userId",
                    LoginManager.getInstance().currentUser?.id
                )
            ).toList().forEach {
                tasks.add(Task(it))
            }

        return tasks
    }

    fun addTask(task: Task) {         //todo: добавление задачи на сервер
        mongoDatabase.getCollection("tasks").insertOne(task.toDocument())
    }


    fun getUserLastId(): Int {     //Todo: определение по последнему элементу БД
        return mongoDatabase.getCollection("users")
            .find().sort(ascending("_id"))
            .first()?.get("_id").toString().toInt()
    }

    fun getTaskLastId(): Int {
        return mongoDatabase.getCollection("tasks")
            .find().sort(ascending("_id"))
            .first()?.get("_id").toString().toInt()
    }


    fun connect(): Status {
        try {
            val connectionString =
                ConnectionString("mongodb+srv://alexey:<Alexey2002%3A%29>@testdb.dlgod.mongodb.net/?retryWrites=true&w=majority")
            val settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(
                    ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build()
                )
                .build()
            mongoClient = MongoClients.create(settings)
            mongoDatabase = mongoClient.getDatabase(databaseName)

//            mongoClient = MongoClients.create(uri)
//            mongoDatabase = mongoClient.getDatabase(databaseName)

            Log.d("DbManagerConnect", "Connected successfully to server.")
            return Status.ALL_OK

        } catch (exception: MongoException) {
            Log.d(
                "DbManagerConnect",
                "An error occurred while attempting to run a command: " + exception
            )
        } catch (namingExc: Exception) {
            Log.e("Catched", namingExc.message.toString())
        }
        return Status.CONNECTION_ERROR
    }

    private fun checkConnection(): Boolean {
        val url = URL("http://testdb.dlgod.mongodb.net")
        with(url.openConnection() as HttpURLConnection) {
            url.openConnection() as HttpURLConnection
            requestMethod = "GET"  // optional default is GET

            Log.d(
                "LOGIN_MANAGER_CHECK_CON",
                "\nSent 'GET' request to URL : $url; Response Code : $responseCode"
            )
            if (responseCode == 200) return true
        }
        return false
    }
}