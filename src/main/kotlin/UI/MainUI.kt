package com.yourpackage.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication


@Composable
fun MainUI(mainViewModel: MainViewModel){
    singleWindowApplication {
        MaterialTheme {
            var grupoId by remember { mutableStateOf("") }
            var gruposInfo by remember { mutableStateOf("Información de todos los grupos") }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = grupoId,
                    onValueChange = { grupoId = it },
                    label = { Text("Grupo ID") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {

                    gruposInfo = "Información del grupo $grupoId"

                    grupoId = ""
                }) {
                    Text("Mostrar")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {

                }) {
                    Text("Exportar")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(gruposInfo)
            }
        }
    }
}
