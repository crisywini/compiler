package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

open class Expression {
    override fun toString(): String {
        return "Expression"
    }
    open fun getTreeView(): TreeItem<String> {
        return TreeItem("Expresion")
    }

    open fun getType(symbolTable:SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit:String):String{
        return ""
    }

    open fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {

    }
    open fun getJavaCode (): String {
        return ""
    }
}