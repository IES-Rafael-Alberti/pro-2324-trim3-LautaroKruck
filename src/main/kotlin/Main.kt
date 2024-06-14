package com.yourpackage

import androidx.compose.ui.window.singleWindowApplication
import com.yourpackage.DAO.SQL.SQLCTFDAO
import com.yourpackage.DAO.SQL.SQLGrupoDAO
import com.yourpackage.db_connection.DBConnection
import com.yourpackage.output.Console
import com.yourpackage.service.CTFService
import com.yourpackage.service.GrupoService
import com.yourpackage.service.MainService
import com.yourpackage.ui.MainUI
import com.yourpackage.ui.MainViewModel
import java.sql.Connection
import java.sql.SQLException

fun main(args: Array<String>) {
    val connection = DBConnection.getConnection()
    createTables(connection)

    val grupoDAO = SQLGrupoDAO()
    val ctfDAO = SQLCTFDAO()
    val output = Console()

    val grupoService = GrupoService(grupoDAO, output)
    val ctfService = CTFService(ctfDAO,grupoDAO, output)

    val mainService = MainService(grupoService, ctfService, output)
    val mainViewModel = MainViewModel(mainService)

    if (args.isNotEmpty()) {
        val command = args[0]
        if (command == "-i") {
            singleWindowApplication {
                MainUI(mainViewModel)
            }
        } else {
            val commandArgs = args.drop(1)
            mainService.processCommand(command, commandArgs)
        }
    } else {
        output.showMessage("Por favor, proporciona un comando.")
    }
}

fun createTables(connection: Connection) {
    val statement = connection.createStatement()
    try {
        val createGrupoTable = """
            CREATE TABLE IF NOT EXISTS GRUPOS (
                grupoid INT PRIMARY KEY,
                grupodesc VARCHAR(255) NOT NULL,
                mejorposCTFid INT
            );
        """.trimIndent()
        val createCTFTable = """
            CREATE TABLE IF NOT EXISTS CTFS (
                CTFid INT,
                grupoid INT,
                puntuacion INT,
                PRIMARY KEY (CTFid, grupoid),
                FOREIGN KEY (grupoid) REFERENCES GRUPOS(grupoid)
            );
        """.trimIndent()
        statement.execute(createGrupoTable)
        statement.execute(createCTFTable)
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        statement.close()
    }
}