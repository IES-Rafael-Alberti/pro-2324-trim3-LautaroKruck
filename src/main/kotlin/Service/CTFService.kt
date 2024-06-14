package com.yourpackage.service

import com.yourpackage.DAO.ICTFDAO
import com.yourpackage.DAO.IGrupoDAO
import com.yourpackage.entity.CTF
import com.yourpackage.output.IOutputInfo
import java.sql.SQLException

// Clase de servicio para gestionar las participaciones en los CTFs
class CTFService(
    private val ctfDAO: ICTFDAO,
    private val grupoDAO: IGrupoDAO,
    private val output: IOutputInfo
) {

    // Método para añadir una participación en un CTF
    fun addCTFParticipation(ctfId: Int, grupoId: Int, puntuacion: Int) {
        // Verifica si el grupo existe
        if (grupoDAO.obtenerGrupoPorId(grupoId) == null) {
            output.showMessage("ERROR: El grupo con ID $grupoId no existe.")
            return
        }

        try {
            // Crea un objeto CTF y lo añade a la base de datos
            val ctf = CTF(ctfId, grupoId, puntuacion)
            ctfDAO.agregarCTF(ctf)
            updateMejorPosCTF(grupoId) // Actualiza la mejor posición del CTF para el grupo
            output.showMessage("Procesado: Añadida la participación en el CTF con id \"$ctfId\" para el grupo \"$grupoId\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al añadir la participación en el CTF. ${e.message}")
        }
    }

    // Método para actualizar una participación en un CTF
    fun updateCTFParticipation(ctfId: Int, grupoId: Int, puntuacion: Int) {
        // Verifica si la participación en el CTF existe
        if (ctfDAO.obtenerCTFPorId(ctfId, grupoId) == null) {
            output.showMessage("ERROR: La participación en el CTF especificada no existe.")
            return
        }

        try {
            // Crea un objeto CTF y lo actualiza en la base de datos
            val ctf = CTF(ctfId, grupoId, puntuacion)
            ctfDAO.actualizarCTF(ctf)
            updateMejorPosCTF(grupoId) // Actualiza la mejor posición del CTF para el grupo
            output.showMessage("Procesado: Actualizada la participación en el CTF con id \"$ctfId\" para el grupo \"$grupoId\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al actualizar la participación en el CTF. ${e.message}")
        }
    }

    // Método para eliminar una participación en un CTF
    fun deleteCTFParticipation(ctfId: Int, grupoId: Int) {
        // Verifica si la participación en el CTF existe
        if (ctfDAO.obtenerCTFPorId(ctfId, grupoId) == null) {
            output.showMessage("ERROR: La participación en el CTF especificada no existe.")
            return
        }

        try {
            // Elimina la participación en el CTF de la base de datos
            ctfDAO.eliminarCTF(ctfId, grupoId)
            updateMejorPosCTF(grupoId) // Actualiza la mejor posición del CTF para el grupo
            output.showMessage("Procesado: Eliminada la participación en el CTF con id \"$ctfId\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al eliminar la participación en el CTF. ${e.message}")
        }
    }

    // Método para verificar si existe una participación en un CTF
    fun existsCTFParticipation(ctfId: Int, grupoId: Int): Boolean {
        return ctfDAO.obtenerCTFPorId(ctfId, grupoId) != null
    }

    // Método para listar las participaciones de un CTF
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
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al listar las participaciones. ${e.message}")
        }
    }

    // Método privado para actualizar la mejor posición del CTF para un grupo
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
