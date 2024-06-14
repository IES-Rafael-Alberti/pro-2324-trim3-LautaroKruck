package com.yourpackage.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.yourpackage.service.MainService
import com.yourpackage.output.Console

class MainViewModel(private val mainService: MainService) {
    var grupoId by mutableStateOf("")
    var output by mutableStateOf("Información de los grupos:")

    fun mostrarInformacion() {
        mainService.output.clearOutput() // Limpiar los mensajes anteriores
        if (grupoId.isBlank()) {
            mainService.grupoService.listGrupo()
        } else {
            val grupoIdInt = grupoId.toIntOrNull()
            if (grupoIdInt != null) {
                mainService.grupoService.listGrupo(grupoIdInt)
            } else {
                mainService.output.showMessage("Por favor, introduce un ID de grupo válido.")
            }
        }
        output = mainService.output.getOutput()
        grupoId = ""
    }

    fun exportarClasificacion() {
        // Implementar la funcionalidad de exportación si es necesario
    }
}
