package com.yourpackage.ui

import com.yourpackage.service.MainService

class MainViewModel(private val mainService: MainService) {

    fun showGrupos() {
        mainService.processCommand("-l", emptyList())
    }

    fun showCTFs() {
        mainService.processCommand("-c", emptyList())
    }

    fun showParticipaciones() {
        mainService.processCommand("-p", emptyList())
    }

    fun guardar() {
        mainService.processCommand("-f", emptyList())
    }
}

