package com.yourpackage.service

import com.yourpackage.entity.Grupo
import com.yourpackage.dao.IGrupoDAO
import com.yourpackage.output.IOutputInfo

class GrupoService(private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {

    fun existsGrupo(grupoId: Int): Boolean {
        return grupoDAO.obtenerGrupoPorId(grupoId) != null
    }

    fun addGrupo(grupoId: Int, grupoDesc: String) {
        try {
            val grupo = Grupo(grupoId, grupoDesc)
            grupoDAO.agregarGrupo(grupo)
            output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\".")
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al añadir el grupo. ${e.message}")
        }
    }

    fun deleteGrupo(grupoId: Int) {
        try {
            grupoDAO.eliminarGrupo(grupoId)
            output.showMessage("Procesado: Eliminado el grupo con id \"$grupoId\".")
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al eliminar el grupo. ${e.message}")
        }
    }

    fun listGrupo(grupoId: Int? = null) {
        try {
            if (grupoId != null) {
                val grupo = grupoDAO.obtenerGrupoPorId(grupoId)
                if (grupo != null) {
                    output.showMessage("Procesado: Listado del grupo ${grupo.grupoId}")
                    output.showMessage("GRUPO: ${grupo.grupoId}   ${grupo.grupoDesc}")
                } else {
                    output.showMessage("No se encontró el grupo con id \"$grupoId\".")
                }
            } else {
                val grupos = grupoDAO.obtenerTodosGrupos()
                if (grupos.isNotEmpty()) {
                    output.showMessage("Procesado: Listado de todos los grupos")
                    grupos.forEach { grupo ->
                        output.showMessage("GRUPO: ${grupo.grupoId}   ${grupo.grupoDesc}")
                    }
                } else {
                    output.showMessage("No hay grupos registrados.")
                }
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al listar los grupos. ${e.message}")
        }
    }
}
