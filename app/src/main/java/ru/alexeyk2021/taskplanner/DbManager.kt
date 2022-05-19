package ru.alexeyk2021.taskplanner


class DbManager {
    companion object {
        private var dbManager: DbManager? = null
        fun getInstance(): DbManager {
            if (dbManager == null)
                dbManager = DbManager()
            return dbManager!!
        }

        enum class Status {
            NO_USER_IN_DB,
            USER_ALREADY_IN_DB,
            INCORRECT_PASSWORD,
            SERVER_ERROR,
            ALL_OK
        }
    }

    public var userId: Int = -1

    fun login(login: String, password: String): Status {
        //todo: Вход по логину и зашифрованному паролю; userId получаем из таблицы

        return Status.NO_USER_IN_DB
    }

    private fun connectToDB(ConnectionData: String) {

    }

    fun register(username: String, login: String, password: String): Status {
        //todo:Регистрация с занесением логина, имени и зашифрованного пароля; генерим новый userId
        return Status.ALL_OK
    }

    fun getNotes(): List<Task> {
        val tasks = mutableListOf<Task>()
        //todo: получение задачи с сервера
        return tasks
    }

    fun addNote(task: Task): Boolean {
        //todo: добавление задачи на сервер
        return true
    }
}