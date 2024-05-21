package com.yourpackage.dao

import com.yourpackage.Entity.Grupo
import com.yourpackage.db_connection.DBConnection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLGrupoDAO : IGrupoDAO {
    private val connection = DBConnection.getConnection()

    override fun addGrupo(grupo: Grupo) {
        val query = "INSERT INTO GRUPOS (grupoid, grupodesc) VALUES (?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, grupo.grupoId)
        preparedStatement.setString(2, grupo.grupoDesc)
        preparedStatement.executeUpdate()
    }

    override fun updateGrupo(grupo: Grupo) {
        val query = "UPDATE GRUPOS SET grupodesc = ? WHERE grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, grupo.grupoDesc)
        preparedStatement.setInt(2, grupo.grupoId)
        preparedStatement.executeUpdate()
    }

    override fun deleteGrupo(grupoId: Int) {
        val query = "DELETE FROM GRUPOS WHERE grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, grupoId)
        preparedStatement.executeUpdate()
    }

    override fun getAllGrupos(): List<Grupo> {
        val query = "SELECT * FROM GRUPOS"
        val statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(query)
        val grupos = mutableListOf<Grupo>()
        while (resultSet.next()) {
            val grupo = Grupo(
                resultSet.getInt("grupoid"),
                resultSet.getString("grupodesc")
            )
            grupos.add(grupo)
        }
        return grupos
    }

    override fun getGrupoById(grupoId: Int): Grupo? {
        val query = "SELECT * FROM GRUPOS WHERE grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, grupoId)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            Grupo(
                resultSet.getInt("grupoid"),
                resultSet.getString("grupodesc")
            )
        } else {
            null
        }
    }
}
