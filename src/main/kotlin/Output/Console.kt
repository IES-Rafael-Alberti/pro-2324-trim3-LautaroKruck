package com.yourpackage.output

class Console : IOutputInfo {
    private val output = mutableListOf<String>()

    // Method to show a message, optionally adding a line break
    override fun showMessage(message: String, lineBreak: Boolean) {
        if (lineBreak) {
            println(message)
            output.add(message)
        } else {
            print(message)
            output.add(message)
        }
    }

    // Method to show a list of items
    override fun show(list: List<Any>) {
        list.forEach {
            println(it)
            output.add(it.toString())
        }
    }

    // Method to get the collected output as a single string
    override fun getOutput(): String {
        return output.joinToString("\n")
    }

    // Method to clear the collected output
    override fun clearOutput() {
        output.clear()
    }
}
