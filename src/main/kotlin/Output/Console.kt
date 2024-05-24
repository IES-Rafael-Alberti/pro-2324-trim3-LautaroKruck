package com.yourpackage.output

class Console : IOutputInfo {

    override fun showMessage(message: String, lineBreak: Boolean) {
        if (lineBreak) {
            println(message)
        } else {
            print(message)
        }
    }

    override fun show(list: List<Any>) {
        list.forEach { println(it) }
    }
}

