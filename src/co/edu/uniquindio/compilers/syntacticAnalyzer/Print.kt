package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Print(var variableName: Token?, var expression: Expression?) :Statement() {
    override fun toString(): String {
        return "Print(variableName=$variableName, expression=$expression)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Imprimir")

        if(variableName != null) {
            root.children.add(TreeItem("Nombre Variable:${variableName!!.lexema}"))
        }

        if(expression != null) {
            var espress= TreeItem("Expresion")
            root.children.add(expression!!.getTreeView())
        }
        return root
    }
}