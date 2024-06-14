package com.yourpackage.service

import com.yourpackage.entity.Grupo
import com.yourpackage.DAO.IGrupoDAO
import com.yourpackage.output.IOutputInfo
import java.sql.SQLException

// Clase de servicio para gestionar los grupos
class GrupoService(
    private val grupoDAO: IGrupoDAO,
    private val output: IOutputInfo
) {

    // Método para verificar si un grupo existe
    fun existsGrupo(grupoId: Int): Boolean {
        return grupoDAO.obtenerGrupoPorId(grupoId) != null
    }

    // Método para añadir un nuevo grupo
    fun addGrupo(grupoId: Int, grupoDesc: String) {
        try {
            // Crea un objeto Grupo y lo añade a la base de datos
            val grupo = Grupo(grupoId, grupoDesc)
            grupoDAO.agregarGrupo(grupo)
            output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al añadir el grupo. ${e.message}")
        }
    }

    // Método para eliminar un grupo
    fun deleteGrupo(grupoId: Int) {
        try {
            grupoDAO.eliminarGrupo(grupoId)
            output.showMessage("Procesado: Eliminado el grupo con id \"$grupoId\".")
        } catch (e: SQLException) {
            output.showMessage("ERROR: Se ha producido un error al eliminar el grupo. ${e.message}")
        }
    }

    // Método para listar grupos
    fun listGrupo(grupoId: Int? = null) {
        try {
            // Si se proporciona un grupoId, obtener solo ese grupo, de lo contrario obtener todos los grupos
            val grupos = if (grupoId != null) {
                listOfNotNull(grupoDAO.obtenerGrupoPorId(grupoId))
            } else {
                grupoDAO.obtenerTodosGrupos()
            }

            // Mostrar los grupos obtenidos
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

    // Método para obtener el último grupoId registrado
    fun obtenerUltimoGrupoId(): Int? {
        return grupoDAO.obtenerUltimoGrupoId()
    }
}
