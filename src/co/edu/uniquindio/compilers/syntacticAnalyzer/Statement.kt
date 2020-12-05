package co.edu.uniquindio.compilers.syntacticAnalyzer
import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

open class Statement {

    open fun getTreeView():TreeItem<String>{
        return TreeItem("Sentencia")
    }

    open fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList:ArrayList<Error>, ambit:String){

    }

    open fun analyzeSemantic(symbolsTable:SymbolsTable, semanticErrorsList:ArrayList<Error>, ambit:String){

    }
    open fun getJavaCode (): String {
        return ""
    }
}