package com.yourpackage.Service

import com.yourpackage.dao.IGrupoDAO
import com.yourpackage.output.IOutputInfo

class GrupoService(private val grupoDAO: IGrupoDAO, private val output: IOutputInfo) {
    fun addGrupo(grupoId: Int, grupoDesc: String) {
        grupoDAO.addGrupo(grupoId, grupoDesc)
        output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\".")
    }

    fun deleteGrupo(grupoId: Int) {
        grupoDAO.deleteGrupo(grupoId)
        output.showMessage("Procesado: Eliminada el grupo con id \"$grupoId\".")
    }

    fun listGrupo(grupoId: Int?) {
        if (grupoId != null) {
            val grupo = grupoDAO.getGrupoById(grupoId)
            if (grupo != null) {
                output.showMessage("Procesado: Listado del grupo ${grupo.grupoId}")
                output.showMessage("GRUPO: ${grupo.grupoId}   ${grupo.grupoDesc}")
            } else {
                output.showMessage("No se encontró el grupo con id \"$grupoId\".")
            }
        } else {
            val grupos = grupoDAO.getAllGrupos()
            if (grupos.isNotEmpty()) {
                output.showMessage("Procesado: Listado de todos los grupos")
                grupos.forEach { grupo ->
                    output.showMessage("GRUPO: ${grupo.grupoId}   ${grupo.grupoDesc}")
                }
            } else {
                output.showMessage("No hay grupos registrados.")
            }
        }
    }
}
