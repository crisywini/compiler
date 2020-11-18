package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView

class VariableDeclaration(var dataType:Token, var identifierList:ArrayList<Token>):Statement() {
    override fun toString(): String {
        return "VariableDeclaration(dataType=$dataType, identifierList=$identifierList)"
    }
    override fun getTreeView(): TreeItem<String> {
        val root: TreeItem<String> = TreeItem("Declaración Variable")

        return root
    }
}