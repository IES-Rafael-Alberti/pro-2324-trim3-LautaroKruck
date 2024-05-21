package com.yourpackage.output

import com.yourpackage.Entity.Grupo

class Console : IOutputInfo {

    override fun showMessage(message: String, lineBreak: Boolean) {
        if (lineBreak) {
            println(message)
        } else {
            print(message)
        }
    }

    override fun show(grupoList: List<Grupo>?, message: String) {
        if (grupoList != null) {
            if (grupoList.isEmpty()) {
                showMessage("No groups found!")
            } else {
                showMessage(message)
                grupoList.forEachIndexed { index, grupo ->
                    showMessage("\t${index + 1}. $grupo")
                }
            }
        }
    }
}

