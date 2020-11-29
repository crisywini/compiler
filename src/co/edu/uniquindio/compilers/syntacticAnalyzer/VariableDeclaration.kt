package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class VariableDeclaration(var dataType:Token, var identifierList:ArrayList<Token>):Statement() {
    override fun toString(): String {
        return "VariableDeclaration(dataType=$dataType, identifierList=$identifierList)"
    }
    override fun getTreeView(): TreeItem<String> {
        val root: TreeItem<String> = TreeItem("Declaración Variable")
        root.children.add(TreeItem("Tipo de dato: ${dataType.lexema}"))
        val identifierRoot:TreeItem<String> = TreeItem<String>("Identificadores")
        for(i in identifierList){
            identifierRoot.children.add(TreeItem(i.lexema))
        }
        root.children.add(identifierRoot)
        return root
    }

    override fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        for(variable in identifierList){
            symbolsTable.saveSymbolValue(variable.lexema, variable.lexema, true, ambit, variable.row, variable.column)
        }
    }
}