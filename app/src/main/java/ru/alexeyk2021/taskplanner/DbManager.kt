package ru.alexeyk2021.taskplanner

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.query
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

    private lateinit var realm: Realm

    fun connect() {
        val config =
            RealmConfiguration.Builder(setOf(Task::class, User::class))
                .name("taskplanner_mttwh").build()
        try {
            realm = Realm.open(config)
            Log.d("Connect to DB", "Successfully opened realm: ${realm.configuration.name}")
        } catch (e: Exception) {
            Log.d("EXCEPTION", e.message.toString())
        }
        val log = realm.configuration.log
        Log.d("END OF CONNECT", "****************************************************")
    }


    suspend fun createUser(user: User): Status {
        return try {
            realm.write {
                this.copyToRealm(user)
            }
            Log.d("DbManager", "Create User")
//            realm.close()
            Status.ALL_OK
        } catch (e: Exception) {
            Status.SERVER_ERROR
        }
    }

    suspend fun deleteUser(user: User): Status {
        return try {
            realm.write {
                val user = this.query<User>("_id == $0", user.id).first().find()
                user?.also { delete(it) }
            }
            Log.d("DbManager", "Delete User")
//            realm.close()
            Status.ALL_OK
        } catch (e: Exception) {
            Status.SERVER_ERROR
        }
    }

    fun findUser(email: String): User? {
        val user = realm.query<User>("email == $0", email).first().find()
        Log.d("DbManager", "Find User")
//        realm.close()
        return user
    }


    fun getUserLastId(): Int {     //Todo: определение по последнему элементу БД
        val lastId = realm.query<User>().sort("_id").first().find()?.id!!
        Log.d("DbManager", "Get user last id")
        return lastId
    }

    fun getUsers(): List<User> {
        val users = realm.query<User>().find()
        return users
    }

    suspend fun createTask(task: Task): Status {
        return try {
            realm.write {
                this.copyToRealm(task)
            }
//            realm.close()
            Status.ALL_OK
        } catch (e: Exception) {
            Status.SERVER_ERROR
        }
    }

    fun getTasks(user: User): List<Task> {
        val tasks = realm.query<Task>("userId == $0", user.id).find()
        Log.d("DbManager", "get tasks")
//        realm.close()
        return tasks
    }

    fun getTaskLastId(): Int {
        val lastId = realm.query<Task>().sort("_id").first().find()?.id!!
        Log.d("DbManager", "Get task last id")
        return lastId
    }

    suspend fun deleteTask(task: Task): Status {
        return try {
            realm.write {
                val task = this.query<User>("_id == $0", task.id).first().find()
                task?.also { delete(it) }
            }
            Log.d("DbManager", "Delete User")
//            realm.close()
            Status.ALL_OK
        } catch (e: Exception) {
            Status.SERVER_ERROR
        }
    }


//    fun checkConnection(): Boolean {
//        val url = URL("http://testdb.dlgod.mongodb.net")
//        with(url.openConnection() as HttpURLConnection) {
//            url.openConnection() as HttpURLConnection
//            requestMethod = "GET"  // optional default is GET
//
//            Log.d(
//                "LOGIN_MANAGER_CHECK_CON",
//                "\nSent 'GET' request to URL : $url; Response Code : $responseCode"
//            )
//            if (responseCode == 200) return true
//        }
//        return false
//    }


//    val realmSync by lazy {
//        App(AppConfiguration.Builder(BuildConfig.RealmAppId).build())
//    }

//    private var databaseName: String = "myDB"
//    private lateinit var mongoClient: MongoClient
//    private lateinit var mongoDatabase: MongoDatabase
//
//    fun findUser(email: String): User? {            // TODO:  поиск клиента в БД по email и возврат его User объекта
//        val users = mongoDatabase.getCollection("users").find(eq("email", email)).toMutableList()
//        if (users.isEmpty()) return null
//        return User(users[0])
//    }
//
//    fun createUser(user: User): Status {
//        val userInfo = user.toDocument()
//        mongoDatabase.getCollection("users").insertOne(userInfo)
//        return Status.ALL_OK
//    }
//
//
//    fun getTasks(): List<Task> {             //todo: получение задачи с сервера
//        val tasks = mutableListOf<Task>()
//        mongoDatabase.getCollection("tasks")
//            .find(
//                eq(
//                    "userId",
//                    LoginManager.getInstance().currentUser?.id
//                )
//            ).toList().forEach {
//                tasks.add(Task(it))
//            }
//
//        return tasks
//    }
//
//    fun addTask(task: Task) {         //todo: добавление задачи на сервер
//        mongoDatabase.getCollection("tasks").insertOne(task.toDocument())
//    }
//
//
//    fun getUserLastId(): Int {     //Todo: определение по последнему элементу БД
//        return mongoDatabase.getCollection("users")
//            .find().sort(ascending("_id"))
//            .first()?.get("_id").toString().toInt()
//    }
//
//    fun getTaskLastId(): Int {
//        return mongoDatabase.getCollection("tasks")
//            .find().sort(ascending("_id"))
//            .first()?.get("_id").toString().toInt()
//    }
//
//
//    fun connect(): Status {
//        try {
//            val connectionString =
//                ConnectionString("mongodb+srv://alexey:<Alexey2002%3A%29>@testdb.dlgod.mongodb.net/?retryWrites=true&w=majority")
//            val settings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .serverApi(
//                    ServerApi.builder()
//                        .version(ServerApiVersion.V1)
//                        .build()
//                )
//                .build()
//            mongoClient = MongoClients.create(settings)
//            mongoDatabase = mongoClient.getDatabase(databaseName)
//
////            mongoClient = MongoClients.create(uri)
////            mongoDatabase = mongoClient.getDatabase(databaseName)
//
//            Log.d("DbManagerConnect", "Connected successfully to server.")
//            return Status.ALL_OK
//
//        } catch (exception: MongoException) {
//            Log.d(
//                "DbManagerConnect",
//                "An error occurred while attempting to run a command: " + exception
//            )
//        } catch (namingExc: Exception) {
//            Log.e("Catched", namingExc.message.toString())
//        }
//        return Status.CONNECTION_ERROR
//    }
//
//

}