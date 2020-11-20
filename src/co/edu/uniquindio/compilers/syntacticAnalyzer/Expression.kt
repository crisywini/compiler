package co.edu.uniquindio.compilers.syntacticAnalyzer

import javafx.scene.control.TreeItem

open class Expression {
    override fun toString(): String {
        return "Expression()"
    }
    fun getTreeView(): TreeItem<String> {
        return TreeItem("Expresion")
    }
}