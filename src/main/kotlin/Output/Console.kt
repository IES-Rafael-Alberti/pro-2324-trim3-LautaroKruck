package com.yourpackage.output

class Console : IOutputInfo {
    private val output = mutableListOf<String>()

    override fun showMessage(message: String, lineBreak: Boolean) {
        if (lineBreak) {
            println(message)
            output.add(message)
        } else {
            print(message)
            output.add(message)
        }
    }

    override fun show(list: List<Any>) {
        list.forEach {
            println(it)
            output.add(it.toString())
        }
    }

    override fun getOutput(): String {
        return output.joinToString("\n")
    }

    override fun clearOutput() {
        output.clear()
    }
}
