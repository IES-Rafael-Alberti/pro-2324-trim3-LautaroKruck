package com.yourpackage.service

import com.yourpackage.dao.IGrupoDAO
import com.yourpackage.Entity.Grupo
import com.yourpackage.dao.ICTFDAO
import com.yourpackage.entity.CTF
import com.yourpackage.output.IOutputInfo

class GrupoService(private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {
    fun addGrupo(grupoId: Int, grupoDesc: String) {
        val grupo = Grupo(grupoId, grupoDesc)
        grupoDAO.addGrupo(grupo)
        output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\".")
    }

    fun deleteGrupo(grupoId: Int) {
        grupoDAO.deleteGrupo(grupoId)
        output.showMessage("Procesado: Eliminada el grupo con id \"$grupoId\".")
    }

    fun listGrupo(grupoId: Int? = null) {
        if (grupoId != null) {
            val grupo = grupoDAO.getGrupoById(grupoId)
            if (grupo != null) {
                output.showMessage("Procesado: Listado del grupo ${grupo.grupoId}")
                output.show(listOf(grupo))
            } else {
                output.showMessage("No se encontró el grupo con id $grupoId.")
            }
        } else {
            val grupos = grupoDAO.getAllGrupos()
            output.show(grupos)
        }
    }
}

class CTFS(private val ctfDAO: ICTFDAO, private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {
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
}