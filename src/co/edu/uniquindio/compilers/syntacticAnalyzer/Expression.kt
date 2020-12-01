package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

open class Expression {
    override fun toString(): String {
        return "Expression"
    }
    open fun getTreeView(): TreeItem<String> {
        return TreeItem("Expresion")
    }

    open fun getType(symbolTable:SymbolsTable, ambit:String):String{
        return ""
    }
}