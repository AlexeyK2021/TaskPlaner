package ru.alexeyk2021.taskplanner


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

    fun createUser(user: User):Boolean{
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

    fun getLastId():Int = 1

}