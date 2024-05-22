package com.yourpackage.db_connection

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private const val URL = "jdbc:h2:mem:test"
    private const val USER = "user"
    private const val PASSWORD = "user"

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}
