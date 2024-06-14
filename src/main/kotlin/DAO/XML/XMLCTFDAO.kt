package com.yourpackage.DAO.XML

import com.yourpackage.entity.CTFParticipation
import com.yourpackage.DAO.ICTFDAO
import com.yourpackage.entity.CTF

class XMLCTFDAO : ICTFDAO {
    override fun agregarCTF(ctf: CTF) {
        TODO("Not yet implemented")
    }

    override fun actualizarCTF(ctf: CTF) {
        TODO("Not yet implemented")
    }

    override fun eliminarCTF(ctfId: Int, grupoId: Int) {
        TODO("Not yet implemented")
    }

    override fun obtenerTodosCTFs(): List<CTF> {
        TODO("Not yet implemented")
    }

    override fun obtenerCTFPorId(ctfId: Int, grupoId: Int): CTF? {
        TODO("Not yet implemented")
    }

    override fun obtenerParticipacionesPorCTFId(ctfId: Int): List<CTFParticipation> {
        TODO("Not yet implemented")
    }
}