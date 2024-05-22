package com.yourpackage.service

import com.yourpackage.dao.ICTFDAO
import com.yourpackage.dao.IGrupoDAO
import com.yourpackage.entity.CTF
import com.yourpackage.output.IOutputInfo

class CTFService(private val ctfDAO: ICTFDAO, private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {
    fun addCTF(CTFid: Int, grupoId: Int, puntuacion: Int) {
        val ctf = CTF(CTFid, grupoId, puntuacion)
        ctfDAO.addCTF(ctf)
        updateMejorPosCTF(grupoId)
        output.showMessage("Procesado: Añadido el CTF con id \"$CTFid\" para el grupo \"$grupoId\".")
    }

    fun updateCTF(CTFid: Int, grupoId: Int, puntuacion: Int) {
        val ctf = CTF(CTFid, grupoId, puntuacion)
        ctfDAO.updateCTF(ctf)
        updateMejorPosCTF(grupoId)
        output.showMessage("Procesado: Modificado el CTF con id \"$CTFid\".")
    }

    fun deleteCTF(CTFid: Int, grupoId: Int) {
        ctfDAO.deleteCTF(CTFid, grupoId)
        updateMejorPosCTF(grupoId)
        output.showMessage("Procesado: Eliminado el CTF con id \"$CTFid\".")
    }

    private fun updateMejorPosCTF(grupoId: Int) {
        val ctfList = ctfDAO.getAllCTFs().filter { it.grupoId == grupoId }
        val mejorCTF = ctfList.maxByOrNull { it.puntuacion }
        val grupo = grupoDAO.getGrupoById(grupoId)
        if (grupo != null) {
            grupo.mejorposCTFid = mejorCTF?.CTFid
            grupoDAO.updateGrupo(grupo)
        }
    }

    fun listParticipaciones(ctfId: Int) {
        val participations = ctfDAO.getParticipationsByCTFId(ctfId)
        if (participations.isEmpty()) {
            output.showMessage("No hay participaciones para el CTF con ID: $ctfId")
        } else {
            participations.forEach { participation ->
                val grupoDesc = grupoDAO.getGrupoDescById(participation.grupoId)
                output.showMessage("CTF ID: ${participation.ctfId}, Grupo ID: ${participation.grupoId}, Grupo Desc: $grupoDesc, Puntuación: ${participation.puntuacion}")
            }
        }
    }
}

