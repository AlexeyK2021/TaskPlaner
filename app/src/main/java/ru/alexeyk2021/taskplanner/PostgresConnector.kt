package ru.alexeyk2021.taskplanner

import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class PostgresConnector() {
    companion object {
        private var postgresConnector: PostgresConnector? = null
        fun getInstance(): PostgresConnector {
            if (postgresConnector == null)
                postgresConnector = PostgresConnector()
            return postgresConnector!!
        }
    }

    private lateinit var conn: Connection

    fun connect() {
//        Class.forName("org.postgresql.Driver");
//        val URL = "jdbc:postgresql://192.168.1.20:5432/testDB"
//        val USER = "alexey"
//        val PASS = "Alexey2002"
//        val conn = DriverManager.getConnection(URL, USER, PASS)
        Class.forName("org.postgresql.Driver")
        runBlocking {
            launch {
                try {
                    conn = DriverManager.getConnection(
                        "jdbc:postgresql://satao.db.elephantsql.com:5432/muanzcrj",
                        "muanzcrj",
                        "fZDE8kZ1QceWSbaEmyysqaW8hl_CQw71"
                    )
                }catch (e:Exception){
                    Log.d("EXCEPTION",e.message.toString())
                }
            }
        }
        Log.d("Connecting", "Status ${conn.isClosed}")
    }

}