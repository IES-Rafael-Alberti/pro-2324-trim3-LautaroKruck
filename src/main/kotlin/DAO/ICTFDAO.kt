package com.yourpackage.dao

import com.yourpackage.entity.CTFParticipation
import com.yourpackage.entity.CTF

interface ICTFDAO {
    fun agregarCTF(ctf: CTF)
    fun actualizarCTF(ctf: CTF)
    fun eliminarCTF(ctfId: Int, grupoId: Int)
    fun obtenerTodosCTFs(): List<CTF>
    fun obtenerCTFPorId(ctfId: Int, grupoId: Int): CTF?
    fun obtenerParticipacionesPorCTFId(ctfId: Int): List<CTFParticipation>
}


