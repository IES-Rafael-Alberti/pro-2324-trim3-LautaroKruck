package com.yourpackage.DAO.SQL

import com.yourpackage.db_connection.DBConnection
import com.yourpackage.entity.Grupo
import com.yourpackage.DAO.IGrupoDAO
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLGrupoDAO : IGrupoDAO {
    // Obtiene la conexión a la base de datos
    private val conexion: Connection = DBConnection.getConnection()

    // Inserta un nuevo registro de Grupo en la base de datos
    override fun agregarGrupo(grupo: Grupo) {
        val consulta = "INSERT INTO GRUPOS (grupoId, grupoDesc) VALUES (?, ?)"
        executeUpdate(consulta, grupo.grupoId, grupo.grupoDesc)
    }

    // Actualiza un registro existente de Grupo en la base de datos
    override fun actualizarGrupo(grupo: Grupo) {
        val consulta = "UPDATE GRUPOS SET grupoDesc = ? WHERE grupoId = ?"
        executeUpdate(consulta, grupo.grupoDesc, grupo.grupoId)
    }

    // Elimina un registro de Grupo de la base de datos basado en su ID
    override fun eliminarGrupo(grupoId: Int) {
        val consulta = "DELETE FROM GRUPOS WHERE grupoId = ?"
        executeUpdate(consulta, grupoId)
    }

    // Recupera todos los registros de Grupo de la base de datos
    override fun obtenerTodosGrupos(): List<Grupo> {
        val consulta = "SELECT * FROM GRUPOS"
        val statement = conexion.createStatement()
        val resultSet: ResultSet = statement.executeQuery(consulta)
        val grupos = mutableListOf<Grupo>()
        resultSet.use {
            while (it.next()) {
                val grupo = Grupo(
                    it.getInt("grupoId"),
                    it.getString("grupoDesc"),
                    it.getInt("mejorposCTFid")
                )
                grupos.add(grupo)
            }
        }
        return grupos
    }

    // Recupera un registro específico de Grupo de la base de datos basado en su ID
    override fun obtenerGrupoPorId(grupoId: Int): Grupo? {
        val consulta = "SELECT * FROM GRUPOS WHERE grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use {
            it.setInt(1, grupoId)
            val resultSet: ResultSet = it.executeQuery()
            resultSet.use {
                return if (it.next()) {
                    Grupo(
                        it.getInt("grupoId"),
                        it.getString("grupoDesc"),
                        it.getInt("mejorposCTFid")
                    )
                } else {
                    null
                }
            }
        }
    }

    // Recupera la descripción de un grupo específico basado en su ID
    override fun obtenerDescripcionGrupoPorId(grupoId: Int): String? {
        val consulta = "SELECT grupoDesc FROM GRUPOS WHERE grupoId = ?"
        val preparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use {
            it.setInt(1, grupoId)
            val resultSet = it.executeQuery()
            resultSet.use {
                return if (it.next()) {
                    it.getString("grupoDesc")
                } else {
                    null
                }
            }
        }
    }

    // Recupera el ID más alto de los grupos en la base de datos
    override fun obtenerUltimoGrupoId(): Int? {
        val consulta = "SELECT MAX(grupoId) AS max_id FROM grupos"
        val preparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use { ps ->
            val resultSet = ps.executeQuery()
            resultSet.use { rs ->
                return if (rs.next()) {
                    rs.getInt("max_id").takeIf { it != 0 }
                } else {
                    null
                }
            }
        }
    }

    // Método privado para ejecutar actualizaciones en la base de datos
    private fun executeUpdate(consulta: String, vararg params: Any) {
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use {
            for ((index, param) in params.withIndex()) {
                it.setObject(index + 1, param)
            }
            it.executeUpdate()
        }
    }
}
