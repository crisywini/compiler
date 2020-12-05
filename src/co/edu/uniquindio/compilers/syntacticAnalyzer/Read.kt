package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.Symbol
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Read(var variableName: Token) :Statement(){

    private var symbol:Symbol?=null

    override fun toString(): String {
        return "Read(variableName=$variableName)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Leer")
        root.children.add(TreeItem("Variable:${variableName.lexema}"))

        return root
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        symbol = symbolsTable.searchSymbolValue(variableName.lexema,ambit)
        if(symbol==null){
            semanticErrorsList.add(Error("El campo (${variableName.lexema}) no existe dentro del ambito ($ambit)", variableName.row, variableName.column, ErrorCategory.ERROR_SEMANTICO))
        }
    }

    override fun getJavaCode(): String {
        return when(symbol!!.type) {
            "becu" -> {
                variableName.getJavaCode() + "= Integer.parseInt(JOptionPane.showInputDialog(null, \"Escribir:\" ));"
            }
            "bemol" -> {
                variableName.getJavaCode() + "= Double.parseDouble(JOptionPane.showInputDialog(null, \"Escribir:\" ));"
            }
            "pulso" -> {
                variableName.getJavaCode() + "= Boolean.parseBoolean(JOptionPane.showInputDialog(null, \"Escribir:\"));"
            }
            else -> {
                variableName.getJavaCode() + "= JOptionPane.showInputDialog(null, \"Escribir:\" );"

            }
        }
    }
}