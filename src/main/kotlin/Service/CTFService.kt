package com.yourpackage.service

import com.yourpackage.dao.ICTFDAO
import com.yourpackage.dao.IGrupoDAO
import com.yourpackage.entity.CTF
import com.yourpackage.output.IOutputInfo

class CTFService(private val ctfDAO: ICTFDAO, private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {

    fun existsCTF(ctfId: Int): Boolean {
        return ctfDAO.obtenerCTFPorId(ctfId, -1) != null
    }

    fun addCTFParticipation(ctfId: Int, grupoId: Int, puntuacion: Int) {
        try {
            val ctf = CTF(ctfId, grupoId, puntuacion)
            ctfDAO.agregarCTF(ctf)
            updateMejorPosCTF(grupoId)
            output.showMessage("Procesado: Añadida la participación en el CTF con id \"$ctfId\" para el grupo \"$grupoId\".")
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al añadir la participación en el CTF. ${e.message}")
        }
    }

    fun deleteCTFParticipation(ctfId: Int, grupoId: Int) {
        try {
            ctfDAO.eliminarCTF(ctfId, grupoId)
            updateMejorPosCTF(grupoId)
            output.showMessage("Procesado: Eliminada la participación en el CTF con id \"$ctfId\".")
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al eliminar la participación en el CTF. ${e.message}")
        }
    }

    fun existsCTFParticipation(ctfId: Int, grupoId: Int): Boolean {
        return ctfDAO.obtenerCTFPorId(ctfId, grupoId) != null
    }

    fun listParticipaciones(ctfId: Int) {
        try {
            val participations = ctfDAO.obtenerParticipacionesPorCTFId(ctfId)
            if (participations.isEmpty()) {
                output.showMessage("No hay participaciones para el CTF con ID $ctfId.")
            } else {
                participations.forEach { participation ->
                    val grupoDesc = grupoDAO.obtenerDescripcionGrupoPorId(participation.grupoId)
                    output.showMessage("CTF ID: ${participation.ctfId}, Grupo ID: ${participation.grupoId}, Grupo Desc: $grupoDesc, Puntuación: ${participation.puntuacion}")
                }
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al listar las participaciones. ${e.message}")
        }
    }

    private fun updateMejorPosCTF(grupoId: Int) {
        val ctfList = ctfDAO.obtenerTodosCTFs().filter { it.grupoId == grupoId }
        val mejorCTF = ctfList.maxByOrNull { it.puntuacion }
        val grupo = grupoDAO.obtenerGrupoPorId(grupoId)
        if (grupo != null) {
            grupo.mejorposCTFid = mejorCTF?.CTFid
            grupoDAO.actualizarGrupo(grupo)
        }
    }
}
