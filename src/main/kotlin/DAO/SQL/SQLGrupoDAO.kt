package com.yourpackage.DAO.SQL

import com.yourpackage.db_connection.DBConnection
import com.yourpackage.entity.Grupo
import com.yourpackage.dao.IGrupoDAO
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLGrupoDAO : IGrupoDAO {
    private val conexion: Connection = DBConnection.getConnection()

    override fun agregarGrupo(grupo: Grupo) {
        val consulta = "INSERT INTO GRUPOS (grupoId, grupoDesc) VALUES (?, ?)"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, grupo.grupoId)
        preparedStatement.setString(2, grupo.grupoDesc)
        preparedStatement.executeUpdate()
    }

    override fun actualizarGrupo(grupo: Grupo) {
        val consulta = "UPDATE GRUPOS SET grupoDesc = ? WHERE grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setString(1, grupo.grupoDesc)
        preparedStatement.setInt(2, grupo.grupoId)
        preparedStatement.executeUpdate()
    }

    override fun eliminarGrupo(grupoId: Int) {
        val consulta = "DELETE FROM GRUPOS WHERE grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, grupoId)
        preparedStatement.executeUpdate()
    }

    override fun obtenerTodosGrupos(): List<Grupo> {
        val consulta = "SELECT * FROM GRUPOS"
        val statement = conexion.createStatement()
        val resultSet: ResultSet = statement.executeQuery(consulta)
        val grupos = mutableListOf<Grupo>()
        while (resultSet.next()) {
            val grupo = Grupo(
                resultSet.getInt("grupoId"),
                resultSet.getString("grupoDesc"),
                resultSet.getInt("mejorposCTFid")
            )
            grupos.add(grupo)
        }
        return grupos
    }

    override fun obtenerGrupoPorId(grupoId: Int): Grupo? {
        val consulta = "SELECT * FROM GRUPOS WHERE grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, grupoId)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            Grupo(
                resultSet.getInt("grupoId"),
                resultSet.getString("grupoDesc"),
                resultSet.getInt("mejorposCTFid")
            )
        } else {
            null
        }
    }

    override fun obtenerDescripcionGrupoPorId(grupoId: Int): String? {
        val consulta = "SELECT grupoDesc FROM GRUPOS WHERE grupoId = ?"
        val preparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, grupoId)
        val resultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            resultSet.getString("grupoDesc")
        } else {
            null
        }
    }
}