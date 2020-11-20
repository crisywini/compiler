package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Read(var variableName: Token) :Statement(){
    override fun toString(): String {
        return "Read(variableName=$variableName)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Leer")
        root.children.add(TreeItem("Variable:${variableName.lexema}"))

        return root
    }
}