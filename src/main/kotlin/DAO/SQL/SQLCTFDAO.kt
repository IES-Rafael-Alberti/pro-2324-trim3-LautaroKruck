package com.yourpackage.DAO.SQL

import com.yourpackage.entity.CTFParticipation
import com.yourpackage.DAO.ICTFDAO
import com.yourpackage.db_connection.DBConnection
import com.yourpackage.entity.CTF
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLCTFDAO : ICTFDAO {
    private val conexion: Connection = DBConnection.getConnection()

    override fun agregarCTF(ctf: CTF) {
        val consulta = "INSERT INTO CTFS (CTFid, grupoId, puntuacion) VALUES (?, ?, ?)"
        executeUpdate(consulta, ctf.CTFid, ctf.grupoId, ctf.puntuacion)
    }

    override fun actualizarCTF(ctf: CTF) {
        val consulta = "UPDATE CTFS SET puntuacion = ? WHERE CTFid = ? AND grupoId = ?"
        executeUpdate(consulta, ctf.puntuacion, ctf.CTFid, ctf.grupoId)
    }

    override fun eliminarCTF(ctfId: Int, grupoId: Int) {
        val consulta = "DELETE FROM CTFS WHERE CTFid = ? AND grupoId = ?"
        executeUpdate(consulta, ctfId, grupoId)
    }

    override fun obtenerTodosCTFs(): List<CTF> {
        val consulta = "SELECT * FROM CTFS"
        val statement = conexion.createStatement()
        val resultSet: ResultSet = statement.executeQuery(consulta)
        val listaCTFs = mutableListOf<CTF>()
        resultSet.use {
            while (it.next()) {
                val ctf = CTF(
                    it.getInt("CTFid"),
                    it.getInt("grupoId"),
                    it.getInt("puntuacion")
                )
                listaCTFs.add(ctf)
            }
        }
        return listaCTFs
    }

    override fun obtenerCTFPorId(ctfId: Int, grupoId: Int): CTF? {
        val consulta = "SELECT * FROM CTFS WHERE CTFid = ? AND grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use {
            it.setInt(1, ctfId)
            it.setInt(2, grupoId)
            val resultSet: ResultSet = it.executeQuery()
            resultSet.use {
                return if (it.next()) {
                    CTF(
                        it.getInt("CTFid"),
                        it.getInt("grupoId"),
                        it.getInt("puntuacion")
                    )
                } else {
                    null
                }
            }
        }
    }

    override fun obtenerParticipacionesPorCTFId(ctfId: Int): List<CTFParticipation> {
        val participaciones = mutableListOf<CTFParticipation>()
        val consulta = "SELECT * FROM CTFS WHERE CTFid = ?"
        val preparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.use {
            it.setInt(1, ctfId)
            val resultSet = it.executeQuery()
            resultSet.use {
                while (it.next()) {
                    val grupoId = it.getInt("grupoId")
                    val puntuacion = it.getInt("puntuacion")
                    participaciones.add(CTFParticipation(ctfId, grupoId, puntuacion))
                }
            }
        }
        return participaciones
    }

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
