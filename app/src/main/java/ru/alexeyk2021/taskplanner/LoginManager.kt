package ru.alexeyk2021.taskplanner

import java.math.BigInteger
import java.security.MessageDigest

enum class Status {
    NO_USER_IN_DB, //findUser(email) == 1
    USER_ALREADY_IN_DB, //findUser(email) != 1
    INCORRECT_PASSWORD, //findUser(email)
    SERVER_ERROR,
    ALL_OK
}

class LoginManager {
    companion object {
        private var loginManager: LoginManager? = null
        fun getInstance(): LoginManager {
            if (loginManager == null)
                loginManager = LoginManager()
            return loginManager!!
        }
    }

    private lateinit var user: User

    fun login(email: String, password: String): Status {
        //todo: Вход по логину и зашифрованному паролю; userId получаем из таблицы
        val user: User? = DbManager.getInstance().findUser(email)

        if (user == null) return Status.NO_USER_IN_DB
        else if (md5(password) == user.password) {
            this.user = user
            return Status.ALL_OK
        }
        return Status.SERVER_ERROR
    }

//    private fun connectToDB(ConnectionData: String) {
//
//    }

    fun register(username: String, email: String, password: String): Status {
        //todo:Регистрация с занесением логина, имени и зашифрованного пароля; генерим новый userId
        val dbManager = DbManager.getInstance()

        val user = User(dbManager.getLastId(), email, password, username, mutableListOf())
        if (dbManager.findUser(email) != null) return Status.USER_ALREADY_IN_DB
        else if (dbManager.createUser(user)) return Status.ALL_OK
        else return Status.SERVER_ERROR
    }

    fun isLogged(): Boolean = user.id > -1

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}

