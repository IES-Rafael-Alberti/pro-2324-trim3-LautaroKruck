package com.yourpackage.output

interface IOutputInfo {
    fun showMessage(message: String, lineBreak: Boolean = true)
    fun show(list: List<Any>)
    fun getOutput(): String
    fun clearOutput()
}