package com.yourpackage.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainUI(mainViewModel: MainViewModel) {
    val grupoIdState = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Grupo ID:")
        // TextField for grupoId input can be added here

        Button(onClick = { mainViewModel.showGrupoInfo(grupoIdState.value.toIntOrNull()) }) {
            Text("Show")
        }
        Button(onClick = { mainViewModel.exportClassification() }) {
            Text("Export")
        }
    }
}
