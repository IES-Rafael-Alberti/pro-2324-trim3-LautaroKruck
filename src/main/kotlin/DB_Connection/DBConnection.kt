package com.yourpackage.db_connection

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private const val URL = "jdbc:h2:./default"  // Database URL
    private const val USER = "user"              // Database user
    private const val PASSWORD = "user"          // Database password

    // Method to get a connection to the database
    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}