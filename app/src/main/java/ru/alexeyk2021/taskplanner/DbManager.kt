package ru.alexeyk2021.taskplanner

import android.util.Log
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document


class DbManager {
    companion object {
        private var dbManager: DbManager? = null
        fun getInstance(): DbManager {
            if (dbManager == null)
                dbManager = DbManager()
            return dbManager!!
        }
    }

    fun findUser(email: String): User? = User(
        -1,
        "none",
        "none",
        "none",
        mutableListOf()
    ) // TODO:  поиск клиента в БД по email и возврат его User объекта

    fun createUser(user: User): Boolean {
        return true
    }

    fun getTasks(userId: Int): List<Task> {
        val tasks = mutableListOf<Task>()
        //todo: получение задачи с сервера
        return tasks
    }

    fun addTask(userId: Int, task: Task): Boolean {
        //todo: добавление задачи на сервер
        return true
    }

    fun getLastId(): Int = 1     //Todo: определение по последнему элементу БД

    fun connectToDb() {
        val uri = "<connection string uri>"
        MongoClients.create(uri).use { mongoClient ->
            val database = mongoClient.getDatabase("sample_mflix")
            val collection: MongoCollection<Document> = database.getCollection("movies")
            val doc: Document = collection.find(eq("title", "Back to the Future")).first() as Document
            Log.d("Connect to DB method", doc.toJson())
        }
    }
}