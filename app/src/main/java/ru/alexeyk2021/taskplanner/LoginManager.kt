package ru.alexeyk2021.taskplanner

import android.util.Log
import ru.alexeyk2021.taskplanner.dataClasses.User
import java.math.BigInteger


import java.security.MessageDigest

enum class Status {
    NO_USER_IN_DB, //findUser(email) == 1
    USER_ALREADY_IN_DB, //findUser(email) != 1
    INCORRECT_PASSWORD, //findUser(email)
    SERVER_ERROR,
    CONNECTION_ERROR,
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

    public var currentUser: User? = null

    fun login(email: String, password: String): Status {
        //todo: Вход по логину и зашифрованному паролю; userId получаем из таблицы
        val dbManager = DbManager.getInstance()
        dbManager.connect()
//        val user: User? = dbManager.findUser(email)

//        if (user == null) return Status.NO_USER_IN_DB
//        else if (md5(password) == user.password) {
//            currentUser = user
//            return Status.ALL_OK
//        }
        return Status.INCORRECT_PASSWORD
    }

//    private fun connectToDB(ConnectionData: String) {
//
//    }

    fun register(username: String, email: String, password: String): Status {
        //todo:Регистрация с занесением логина, имени и зашифрованного пароля; генерим новый userId

//        val dbManager = DbManager.getInstance()
//        val encryptedPassword = md5(password)
//        if (dbManager.connect() != Status.ALL_OK) Log.d("Connect", "Connection error!!!")
//        val user = User(dbManager.getUserLastId() + 1, email, encryptedPassword, username)
//        if (dbManager.findUser(email) != null)
//            return Status.USER_ALREADY_IN_DB
//        else return dbManager.createUser(user)
        return Status.ALL_OK
    }

    fun isLogged(): Boolean = currentUser != null

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}

