package com.yourpackage.dao

import com.yourpackage.entity.Grupo


interface IGrupoDAO {
    fun agregarGrupo(grupo: Grupo)
    fun actualizarGrupo(grupo: Grupo)
    fun eliminarGrupo(grupoId: Int)
    fun obtenerTodosGrupos(): List<Grupo>
    fun obtenerGrupoPorId(grupoId: Int): Grupo?
    fun obtenerDescripcionGrupoPorId(grupoId: Int): String?
}


