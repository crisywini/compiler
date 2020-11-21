package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Assignment(var identifier:Token, var expression:Expression):Statement() {
    override fun toString(): String {
        return "Assignment(identifier=$identifier, expression=$expression)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Asignacion")
        root.children.add(TreeItem("Variable:${identifier.lexema}"))
        root.children.add(expression.getTreeView())

        return root
    }
}