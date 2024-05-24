package com.yourpackage.service

import com.yourpackage.output.Console
import com.yourpackage.output.IOutputInfo
import com.yourpackage.service.CTFService
import com.yourpackage.service.GrupoService
import java.io.File

class MainService(
    private val grupoService: GrupoService,
    private val ctfService: CTFService,
    private val output: IOutputInfo
) {
    fun processCommand(command: String, args: List<String>) {
        try {
            when (command) {
                "-g" -> añadirGrupo(args)
                "-t" -> eliminarGrupo(args)
                "-l" -> buscarGrupo(args)
                "-p" -> añadirCTFPart(args)
                "-e" -> eliminarCTFPart(args)
                "-c" -> listaCTFPart(args)
                "-f" -> comandosFile(args)
                "-i" -> Interfaz()
                else -> output.showMessage("Comando no reconocido: $command")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al procesar el comando. ${e.message}")
        }
    }

    private fun añadirGrupo(args: List<String>) {
        try {
            if (args.size == 2) {
                val grupoId = args[0].toIntOrNull()
                if (grupoId != null) {
                    val grupoDesc = args[1]
                    if (!grupoService.existsGrupo(grupoId)) {
                        grupoService.addGrupo(grupoId, grupoDesc)
                        output.showMessage("Grupo agregado exitosamente.")
                    } else {
                        output.showMessage("ERROR: El grupo con ID $grupoId ya existe.")
                    }
                } else {
                    output.showMessage("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
                }
            } else {
                output.showMessage("ERROR: El número de parámetros no es adecuado.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al agregar el grupo. ${e.message}")
        }
    }

    private fun eliminarGrupo(args: List<String>) {
        try {
            if (args.size == 1) {
                val grupoId = args[0].toIntOrNull()
                if (grupoId != null) {
                    if (grupoService.existsGrupo(grupoId)) {
                        grupoService.deleteGrupo(grupoId)
                        output.showMessage("Grupo eliminado exitosamente.")
                    } else {
                        output.showMessage("ERROR: El grupo con ID $grupoId no existe.")
                    }
                } else {
                    output.showMessage("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
                }
            } else {
                output.showMessage("ERROR: El número de parámetros no es adecuado.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al eliminar el grupo. ${e.message}")
        }
    }

    private fun buscarGrupo(args: List<String>) {
        try {
            val grupoId = args.firstOrNull()?.toIntOrNull()
            grupoService.listGrupo(grupoId)
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al buscar el grupo. ${e.message}")
        }
    }

    private fun añadirCTFPart(args: List<String>) {
        try {
            if (args.size == 3) {
                val ctfId = args[0].toIntOrNull()
                val grupoId = args[1].toIntOrNull()
                val puntuacion = args[2].toIntOrNull()
                if (ctfId != null && grupoId != null && puntuacion != null) {
                    if (grupoService.existsGrupo(grupoId) && ctfService.existsCTF(ctfId)) {
                        ctfService.addCTFParticipation(ctfId, grupoId, puntuacion)
                        output.showMessage("Participación en CTF agregada exitosamente.")
                    } else {
                        output.showMessage("ERROR: El grupo o CTF especificado no existe.")
                    }
                } else {
                    output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
                }
            } else {
                output.showMessage("ERROR: El número de parámetros no es adecuado.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al añadir la participación en el CTF. ${e.message}")
        }
    }

    private fun eliminarCTFPart(args: List<String>) {
        try {
            if (args.size == 2) {
                val ctfId = args[0].toIntOrNull()
                val grupoId = args[1].toIntOrNull()
                if (ctfId != null && grupoId != null) {
                    if (ctfService.existsCTFParticipation(ctfId, grupoId)) {
                        ctfService.deleteCTFParticipation(ctfId, grupoId)
                        output.showMessage("Participación en CTF eliminada exitosamente.")
                    } else {
                        output.showMessage("ERROR: La participación en el CTF especificada no existe.")
                    }
                } else {
                    output.showMessage("ERROR: Los parámetros deben ser valores numéricos de tipo entero.")
                }
            } else {
                output.showMessage("ERROR: El número de parámetros no es adecuado.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al eliminar la participación en el CTF. ${e.message}")
        }
    }

    private fun listaCTFPart(args: List<String>) {
        try {
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
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al listar las participaciones en el CTF. ${e.message}")
        }
    }

    private fun comandosFile(args: List<String>) {
        try {
            if (args.size == 1) {
                val filePath = args[0]
                executeCommandsFromFile(filePath)
            } else {
                output.showMessage("ERROR: El número de parámetros no es adecuado.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al procesar el archivo de comandos. ${e.message}")
        }
    }

    private fun Interfaz() {
        // Lanzar la interfaz gráfica
    }

    private fun executeCommandsFromFile(filePath: String) {
        try {
            val file = File(filePath)
            if (file.exists() && file.isFile) {
                file.forEachLine { line ->
                    val parts = line.split(" ")
                    val command = parts[0]
                    val args = parts.drop(1)
                    processCommand(command, args)
                }
            } else {
                output.showMessage("ERROR: El archivo no existe o no es un archivo válido.")
            }
        } catch (e: Exception) {
            output.showMessage("ERROR: Se ha producido un error al leer el archivo. ${e.message}")
        }
    }
}
