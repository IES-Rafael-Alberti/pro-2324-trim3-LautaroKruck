package com.yourpackage.service

import com.yourpackage.output.IOutputInfo

class MainService(
    private val grupoService: GrupoService,
    private val ctfService: CTFService,
    private val output: IOutputInfo
) {
    fun processCommand(command: String, args: List<String>) {
        when (command) {
            "-g" -> {
                if (args.size == 2) {
                    val grupoId = args[0].toIntOrNull()
                    if (grupoId != null) {
                        val grupoDesc = args[1]
                        grupoService.addGrupo(grupoId, grupoDesc)
                    } else {
                        output.showMessage("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
                    }
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-t" -> {
                if (args.size == 1) {
                    val grupoId = args[0].toIntOrNull()
                    if (grupoId != null) {
                        grupoService.deleteGrupo(grupoId)
                    } else {
                        output.showMessage("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
                    }
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-l" -> {
                val grupoId = args.firstOrNull()?.toIntOrNull()
                grupoService.listGrupo(grupoId)
            }
            "-p" -> {
                if (args.size == 3) {
                    val ctfId = args[0].toIntOrNull()
                    val grupoId = args[1].toIntOrNull()
                    val puntuacion = args[2].toIntOrNull()
                    if (ctfId != null && grupoId != null && puntuacion != null) {
                        ctfService.addCTF(ctfId, grupoId, puntuacion)
                    } else {
                        output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
                    }
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-e" -> {
                if (args.size == 2) {
                    val ctfId = args[0].toIntOrNull()
                    val grupoId = args[1].toIntOrNull()
                    if (ctfId != null && grupoId != null) {
                        ctfService.deleteCTF(ctfId, grupoId)
                    } else {
                        output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
                    }
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-c" -> {
                if (args.size == 1) {
                    val ctfId = args[0].toIntOrNull()
                    if (ctfId != null) {
                        ctfService.listParticipaciones(ctfId)
                    } else {
                        output.showMessage("ERROR: El parámetro <ctfId> debe ser un valor numérico de tipo entero.")
                    }
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-f" -> {
                if (args.size == 1) {
                    // Procesar el archivo
                } else {
                    output.showMessage("ERROR: El número de parámetros no es adecuado.")
                }
            }
            "-i" -> {
                // Lanzar la interfaz gráfica
            }
            else -> {
                output.showMessage("Comando no reconocido: $command")
            }
        }
    }
    fun exportClassification(filePath: String) {
        // Implement the export functionality here
        output.showMessage("Exporting classification to $filePath")
    }
}
