package com.yourpackage.DAO.TxtFile

import com.yourpackage.entity.Grupo
import com.yourpackage.DAO.IGrupoDAO


class TextFileGrupoDAO : IGrupoDAO {
    override fun agregarGrupo(grupo: Grupo) {
        TODO("Not yet implemented")
    }

    override fun actualizarGrupo(grupo: Grupo) {
        TODO("Not yet implemented")
    }

    override fun eliminarGrupo(grupoId: Int) {
        TODO("Not yet implemented")
    }

    override fun obtenerTodosGrupos(): List<Grupo> {
        TODO("Not yet implemented")
    }

    override fun obtenerGrupoPorId(grupoId: Int): Grupo? {
        TODO("Not yet implemented")
    }

    override fun obtenerDescripcionGrupoPorId(grupoId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun obtenerUltimoGrupoId(): Int? {
        TODO("Not yet implemented")
    }
}