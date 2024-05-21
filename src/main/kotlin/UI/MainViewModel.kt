package com.yourpackage.UI

import com.yourpackage.Service.MainService

class MainViewModel {
    private val mainService = MainService()

    fun processCommand(command: String) {
        mainService.processCommand(command)
    }

    fun exportClassification(filePath: String) {
        mainService.exportClassification(filePath)
    }
}
