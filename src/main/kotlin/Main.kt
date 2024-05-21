package com.yourpackage

import androidx.compose.desktop.DesktopTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Window
import com.yourpackage.UI.MainUI
import com.yourpackage.UI.MainViewModel

fun main() {
    val mainViewModel = remember { MainViewModel() }

    Window(
        title = "CTF Score Tracker",
        size = IntSize(400, 200)
    ) {
        DesktopTheme {
            MainUI(mainViewModel)
        }
    }
}

