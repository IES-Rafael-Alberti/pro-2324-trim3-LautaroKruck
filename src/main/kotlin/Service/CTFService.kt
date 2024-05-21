package com.yourpackage.service

import com.yourpackage.dao.ICTFDAO
import com.yourpackage.output.IOutputInfo


class CTFService(private val ctfDAO: ICTFDAO, private val output: IOutputInfo) {
    fun addCTFParticipation(ctfId: Int, grupoId: Int, puntuacion: Int) {
        val existingCTF = ctfDAO.getCTFById(ctfId, grupoId)
        if (existingCTF != null) {
            val oldScore = existingCTF.puntuacion
            ctfDAO.updateCTF(ctfId, grupoId, puntuacion)
            output.showMessage("Procesado: Actualizada la participación del grupo $grupoId en el CTF $ctfId. La puntuación cambió de $oldScore a $puntuacion puntos.")
        } else {
            ctfDAO.addCTF(ctfId, grupoId, puntuacion)
            output.showMessage("Procesado: Añadida participación del grupo $grupoId en el CTF $ctfId con una puntuación de $puntuacion puntos.")
        }
        recalculateBestCTF(grupoId)
    }

    fun deleteCTFParticipation(ctfId: Int, grupoId: Int) {
        ctfDAO.deleteCTF(ctfId, grupoId)
        output.showMessage("Procesado: Eliminada participación del grupo $grupoId en el CTF $ctfId.")
        recalculateBestCTF(grupoId)
    }

    fun listCTFParticipation(ctfId: Int) {
        val ctfList = ctfDAO.getCTFById(ctfId)
        if (ctfList.isNotEmpty()) {
            output.showMessage("Procesado: Listado participación en el CTF $ctfId")
            ctfList.forEach { ctf ->
                output.showMessage("GRUPO: ${ctf.grupoId}, Puntuación: ${ctf.puntuacion}")
            }
        } else {
            output.showMessage("No hay participaciones para el CTF $ctfId.")
        }
    }

    private fun recalculateBestCTF(grupoId: Int) {
        val bestCTF = ctfDAO.getBestCTFForGrupo(grupoId)
        ctfDAO.updateBestCTF(grupoId, bestCTF?.ctfId)
    }
}

