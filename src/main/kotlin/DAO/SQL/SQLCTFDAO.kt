package com.yourpackage.DAO.SQL

import com.yourpackage.entity.CTFParticipation
import com.yourpackage.dao.ICTFDAO
import com.yourpackage.db_connection.DBConnection
import com.yourpackage.entity.CTF
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLCTFDAO : ICTFDAO {
    private val conexion: Connection = DBConnection.getConnection()

    override fun agregarCTF(ctf: CTF) {
        val consulta = "INSERT INTO CTFS (CTFid, grupoId, puntuacion) VALUES (?, ?, ?)"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, ctf.CTFid)
        preparedStatement.setInt(2, ctf.grupoId)
        preparedStatement.setInt(3, ctf.puntuacion)
        preparedStatement.executeUpdate()
    }

    override fun actualizarCTF(ctf: CTF) {
        val consulta = "UPDATE CTFS SET puntuacion = ? WHERE CTFid = ? AND grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, ctf.puntuacion)
        preparedStatement.setInt(2, ctf.CTFid)
        preparedStatement.setInt(3, ctf.grupoId)
        preparedStatement.executeUpdate()
    }

    override fun eliminarCTF(ctfId: Int, grupoId: Int) {
        val consulta = "DELETE FROM CTFS WHERE CTFid = ? AND grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, ctfId)
        preparedStatement.setInt(2, grupoId)
        preparedStatement.executeUpdate()
    }

    override fun obtenerTodosCTFs(): List<CTF> {
        val consulta = "SELECT * FROM CTFS"
        val statement = conexion.createStatement()
        val resultSet: ResultSet = statement.executeQuery(consulta)
        val listaCTFs = mutableListOf<CTF>()
        while (resultSet.next()) {
            val ctf = CTF(
                resultSet.getInt("CTFid"),
                resultSet.getInt("grupoId"),
                resultSet.getInt("puntuacion")
            )
            listaCTFs.add(ctf)
        }
        return listaCTFs
    }

    override fun obtenerCTFPorId(ctfId: Int, grupoId: Int): CTF? {
        val consulta = "SELECT * FROM CTFS WHERE CTFid = ? AND grupoId = ?"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, ctfId)
        preparedStatement.setInt(2, grupoId)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            CTF(
                resultSet.getInt("CTFid"),
                resultSet.getInt("grupoId"),
                resultSet.getInt("puntuacion")
            )
        } else {
            null
        }
    }

    override fun obtenerParticipacionesPorCTFId(ctfId: Int): List<CTFParticipation> {
        val participaciones = mutableListOf<CTFParticipation>()
        val consulta = "SELECT * FROM CTFS WHERE CTFid = ?"
        val preparedStatement = conexion.prepareStatement(consulta)
        preparedStatement.setInt(1, ctfId)
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            val grupoId = resultSet.getInt("grupoId")
            val puntuacion = resultSet.getInt("puntuacion")
            participaciones.add(CTFParticipation(ctfId, grupoId, puntuacion))
        }
        return participaciones
    }
}