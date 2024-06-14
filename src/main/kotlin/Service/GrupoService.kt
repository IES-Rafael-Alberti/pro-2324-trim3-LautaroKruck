package com.yourpackage.service

import com.yourpackage.entity.Grupo
import com.yourpackage.DAO.IGrupoDAO
import com.yourpackage.output.IOutputInfo
import java.sql.SQLException

class GrupoService(
    private val grupoDAO: IGrupoDAO,
    private val output: IOutputInfo
) {

    fun existsGrupo(grupoId: Int): Boolean {
        return grupoDAO.obtenerGrupoPorId(grupoId) != null
    }

    fun addGrupo(grupoId: Int, grupoDesc: String) {
        try {
            val grupo = Grupo(grupoId, grupoDesc)
            grupoDAO.agregarGrupo(grupo)
            output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al añadir el grupo. ${e.message}")
        }
    }

    fun deleteGrupo(grupoId: Int) {
        try {
            grupoDAO.eliminarGrupo(grupoId)
            output.showMessage("Procesado: Eliminado el grupo con id \"$grupoId\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al eliminar el grupo. ${e.message}")
        }
    }

    fun listGrupo(grupoId: Int? = null) {
        try {
            val grupos = if (grupoId != null) {
                listOfNotNull(grupoDAO.obtenerGrupoPorId(grupoId))
            } else {
                grupoDAO.obtenerTodosGrupos()
            }

            if (grupos.isNotEmpty()) {
                output.showMessage("Procesado: Listado de ${if (grupoId != null) "del grupo" else "todos los grupos"}")
                grupos.forEach { grupo ->
                    output.showMessage("GRUPO: ${grupo.grupoId}   ${grupo.grupoDesc}")
                }
            } else {
                output.showMessage("No ${if (grupoId != null) "se encontró el grupo" else "hay grupos registrados"} con id \"$grupoId\".")
            }
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al listar los grupos. ${e.message}")
        }
    }

    fun obtenerUltimoGrupoId(): Int? {
        return grupoDAO.obtenerUltimoGrupoId()
    }
}
