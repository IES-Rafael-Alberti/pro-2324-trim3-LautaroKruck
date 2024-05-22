package com.yourpackage.dao

import com.yourpackage.Entity.Grupo

interface IGrupoDAO {
    fun addGrupo(grupo: Grupo)
    fun updateGrupo(grupo: Grupo)
    fun deleteGrupo(grupoId: Int)
    fun getAllGrupos(): List<Grupo>
    fun getGrupoById(grupoId: Int): Grupo?
    fun getGrupoDescById(grupoId: Int): String?
}


