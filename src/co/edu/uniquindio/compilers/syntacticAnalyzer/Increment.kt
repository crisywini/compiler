package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Increment(var variableName: Token):Statement(){
    override fun toString(): String {
        return "Increment(variableName=$variableName)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Incrementar")

        root.children.add(TreeItem("Nombre Variable:${variableName.lexema}"))
        return root;
    }
}