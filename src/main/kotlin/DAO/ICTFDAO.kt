package com.yourpackage.dao

import com.yourpackage.entity.CTF

interface ICTFDAO {
    fun addCTF(ctf: CTF)
    fun updateCTF(ctf: CTF)
    fun deleteCTF(ctfId: Int, grupoId: Int)
    fun getAllCTFs(): List<CTF>
    fun getCTFById(ctfId: Int, grupoId: Int): CTF?
}


