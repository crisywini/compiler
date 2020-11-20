package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Decrement (var variableName: Token):Statement(){
    override fun toString(): String {
        return "Decrement(variableName=$variableName)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Decrementar")

        root.children.add(TreeItem("Nombre Variable:${variableName.lexema}"))
        return root;
    }
}