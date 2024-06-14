package com.yourpackage.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainUI(viewModel: MainViewModel) {
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Text(viewModel.output)
                }
            }
            OutlinedTextField(
                value = viewModel.grupoId,
                onValueChange = { viewModel.grupoId = it },
                label = { Text("Grupo ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    viewModel.mostrarInformacion()
                }) {
                    Text("Mostrar")
                }
                Button(onClick = {
                    viewModel.exportarClasificacion()
                }) {
                    Text("Exportar")
                }
            }
        }
    }
}
