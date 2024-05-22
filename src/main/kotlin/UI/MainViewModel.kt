package com.yourpackage.ui

import com.yourpackage.service.MainService

class MainViewModel(private val mainService: MainService) {

    fun showGrupoInfo(grupoId: Int?) {
        if (grupoId != null) {
            mainService.processCommand("-l", listOf(grupoId.toString()))
        } else {
            // Handle invalid groupId input
        }
    }

    fun exportClassification() {
        // Define a default file path or get it from the UI
        val filePath = "classification_export.txt"
        mainService.exportClassification(filePath)
    }
}
