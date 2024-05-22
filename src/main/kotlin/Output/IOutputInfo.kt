package com.yourpackage.output

import com.yourpackage.Entity.Grupo

interface IOutputInfo {
    fun showMessage(message: String, lineBreak: Boolean = true)
    fun show(list: List<Any>)
}