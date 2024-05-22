package com.yourpackage.dao


import com.yourpackage.Entity.CTFParticipation
import com.yourpackage.db_connection.DBConnection
import com.yourpackage.entity.CTF
import com.yourpackage.Entity.Grupo
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLCTFDAO : ICTFDAO {
    private val connection: Connection = DBConnection.getConnection()

    override fun addCTF(ctf: CTF) {
        val query = "INSERT INTO CTFS (CTFid, grupoid, puntuacion) VALUES (?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, ctf.CTFid)
        preparedStatement.setInt(2, ctf.grupoId)
        preparedStatement.setInt(3, ctf.puntuacion)
        preparedStatement.executeUpdate()
    }

    override fun updateCTF(ctf: CTF) {
        val query = "UPDATE CTFS SET puntuacion = ? WHERE CTFid = ? AND grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, ctf.puntuacion)
        preparedStatement.setInt(2, ctf.CTFid)
        preparedStatement.setInt(3, ctf.grupoId)
        preparedStatement.executeUpdate()
    }

    override fun deleteCTF(ctfId: Int, grupoId: Int) {
        val query = "DELETE FROM CTFS WHERE CTFid = ? AND grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, ctfId)
        preparedStatement.setInt(2, grupoId)
        preparedStatement.executeUpdate()
    }

    override fun getAllCTFs(): List<CTF> {
        val query = "SELECT * FROM CTFS"
        val statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(query)
        val ctfList = mutableListOf<CTF>()
        while (resultSet.next()) {
            val ctf = CTF(
                resultSet.getInt("CTFid"),
                resultSet.getInt("grupoid"),
                resultSet.getInt("puntuacion")
            )
            ctfList.add(ctf)
        }
        return ctfList
    }

    override fun getCTFById(ctfId: Int, grupoId: Int): CTF? {
        val query = "SELECT * FROM CTFS WHERE CTFid = ? AND grupoid = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, ctfId)
        preparedStatement.setInt(2, grupoId)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        return if (resultSet.next()) {
            CTF(
                resultSet.getInt("CTFid"),
                resultSet.getInt("grupoid"),
                resultSet.getInt("puntuacion")
            )
        } else {
            null
        }
    }
    override fun getParticipationsByCTFId(ctfId: Int): List<CTFParticipation> {
        val participations = mutableListOf<CTFParticipation>()
        val query = "SELECT * FROM CTFS WHERE CTFid = ?"
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, ctfId)
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            val grupoId = resultSet.getInt("grupoid")
            val puntuacion = resultSet.getInt("puntuacion")
            participations.add(CTFParticipation(ctfId, grupoId, puntuacion))
        }
        return participations
    }
}
