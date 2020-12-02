package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
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

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        var symbol = symbolsTable.searchSymbolValue(variableName.lexema,ambit)
        if(symbol==null){
            semanticErrorsList.add(Error("El campo (${variableName.lexema}) no existe dentro del ambito ($ambit)", variableName.row, variableName.column, ErrorCategory.ERROR_SEMANTICO))
        }
    }
}