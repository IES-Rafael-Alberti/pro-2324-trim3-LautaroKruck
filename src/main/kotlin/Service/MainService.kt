package com.yourpackage.service

import com.yourpackage.output.Console
import com.yourpackage.output.IOutputInfo
import com.yourpackage.service.CTFService
import com.yourpackage.service.GrupoService
import java.io.File
import java.io.IOException

// Clase de servicio principal para procesar comandos
class MainService(
    val grupoService: GrupoService,
    private val ctfService: CTFService,
    val output: IOutputInfo
) {

    // Método para procesar comandos
    fun processCommand(command: String, args: List<String>) {
        try {
            when (command) {
                "-g" -> añadirGrupo(args) // Añadir grupo
                "-t" -> eliminarGrupo(args) // Eliminar grupo
                "-l" -> buscarGrupo(args) // Listar grupo
                "-p" -> añadirCTFPart(args) // Añadir participación en CTF
                "-e" -> eliminarCTFPart(args) // Eliminar participación en CTF
                "-c" -> listaCTFPart(args) // Listar participaciones en CTF
                "-f" -> comandosFile(args) // Procesar comandos desde un archivo
                "-i" -> Interfaz() // Iniciar interfaz gráfica
                else -> output.showMessage("Comando no reconocido: $command") // Comando no reconocido
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al procesar el comando. ${e.message}")
        }
    }

    // Método privado para añadir un grupo
    private fun añadirGrupo(args: List<String>) {
        if (args.size != 1) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val grupoDesc = args[0]

        try {
            val ultimoGrupoId = grupoService.obtenerUltimoGrupoId() ?: 0
            val nuevoGrupoId = ultimoGrupoId + 1

            if (!grupoService.existsGrupo(nuevoGrupoId)) {
                grupoService.addGrupo(nuevoGrupoId, grupoDesc)
                output.showMessage("Procesado: Añadido el grupo \"$grupoDesc\" con ID $nuevoGrupoId.")
            } else {
                output.showMessage("ERROR: El grupo con ID $nuevoGrupoId ya existe.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al añadir el grupo. ${e.message}")
        }
    }

    // Método privado para eliminar un grupo
    private fun eliminarGrupo(args: List<String>) {
        if (args.size != 1) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val grupoId = args[0].toIntOrNull()
        if (grupoId == null) {
            output.showMessage("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
            return
        }

        if (grupoService.existsGrupo(grupoId)) {
            grupoService.deleteGrupo(grupoId)
        } else {
            output.showMessage("ERROR: El grupo con ID $grupoId no existe.")
        }
    }

    // Método privado para buscar un grupo
    private fun buscarGrupo(args: List<String>) {
        val grupoId = args.firstOrNull()?.toIntOrNull()
        grupoService.listGrupo(grupoId)
    }

    // Método privado para añadir una participación en CTF
    private fun añadirCTFPart(args: List<String>) {
        if (args.size != 3) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val ctfId = args[0].toIntOrNull()
        val grupoId = args[1].toIntOrNull()
        val puntuacion = args[2].toIntOrNull()

        if (ctfId == null || grupoId == null || puntuacion == null) {
            output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
            return
        }

        if (grupoService.existsGrupo(grupoId)) {
            if (ctfService.existsCTFParticipation(ctfId, grupoId)) {
                ctfService.updateCTFParticipation(ctfId, grupoId, puntuacion)
            } else {
                ctfService.addCTFParticipation(ctfId, grupoId, puntuacion)
            }
        } else {
            output.showMessage("ERROR: El grupo o CTF especificado no existe.")
        }
    }

    // Método privado para eliminar una participación en CTF
    private fun eliminarCTFPart(args: List<String>) {
        if (args.size != 2) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val ctfId = args[0].toIntOrNull()
        val grupoId = args[1].toIntOrNull()

        if (ctfId == null || grupoId == null) {
            output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
            return
        }

        if (ctfService.existsCTFParticipation(ctfId, grupoId)) {
            ctfService.deleteCTFParticipation(ctfId, grupoId)
        } else {
            output.showMessage("ERROR: La participación en el CTF especificada no existe.")
        }
    }

    // Método privado para listar participaciones en CTF
    private fun listaCTFPart(args: List<String>) {
        if (args.size != 1) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val ctfId = args[0].toIntOrNull()
        if (ctfId != null) {
            ctfService.listParticipaciones(ctfId)
        } else {
            output.showMessage("ERROR: El parámetro <ctfId> debe ser un valor numérico de tipo entero.")
        }
    }

    // Método privado para procesar comandos desde un archivo
    private fun comandosFile(args: List<String>) {
        if (args.size != 1) {
            output.showMessage("ERROR: El número de parámetros no es adecuado.")
            return
        }

        val filePath = args[0]
        executeCommandsFromFile(filePath)
    }

    // Método privado para iniciar la interfaz gráfica
    private fun Interfaz() {
        // Lanzar la interfaz gráfica
    }

    // Método para ejecutar comandos desde un archivo
    fun executeCommandsFromFile(filePath: String) {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            output.showMessage("ERROR: El archivo no existe o no es un archivo válido.")
            return
        }

        try {
            var currentCommand: String? = null
            val args = mutableListOf<String>()

            file.forEachLine { line ->
                val trimmedLine = line.trim()

                // Ignorar comentarios y líneas vacías
                if (trimmedLine.startsWith("#") || trimmedLine.isEmpty()) {
                    return@forEachLine
                }

                // Si la línea es un comando (empieza con "-")
                if (trimmedLine.startsWith("-")) {
                    // Ejecutar el comando previo, si existe
                    currentCommand?.let {
                        processCommand(it, args.toList())
                    }

                    // Actualizar el comando actual y limpiar los argumentos
                    currentCommand = trimmedLine
                    args.clear()
                } else {
                    // Agregar la línea como argumento del comando actual
                    args.add(trimmedLine)
                }
            }

            // Ejecutar el último comando
            currentCommand?.let {
                processCommand(it, args.toList())
            }
        } catch (e: IOException) {
            output.showMessage("ERROR: Se ha producido un error al leer el archivo. ${e.message}")
        }
    }
}
