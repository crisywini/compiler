package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Case (var dataType: Token, var statement: Statement){
    override fun toString(): String {
        return "Argument(dataType=$dataType, statement=$statement)"
    }
    fun getTreeView(): TreeItem<String> {
        var treeData = TreeItem(dataType.lexema)
        var treeStatement = statement.getTreeView()

        var tree = TreeItem("Casos")
        tree.children.add(treeData)
        tree.children.add(treeStatement)

        return tree
    }
}