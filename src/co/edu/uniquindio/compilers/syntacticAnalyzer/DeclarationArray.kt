package co.edu.uniquindio.compilers.syntacticAnalyzer;

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class DeclarationArray(var dataType:Token, var name: Token, var initialization: InitializationArray?):Statement() {
    override fun toString(): String {
        return "declarationArray(dataType=$dataType, name=$name, initialization=$initialization)"
    }
    override fun getTreeView(): TreeItem<String> {
        val declaration: TreeItem<String> = TreeItem("Declaración Arreglo")
        declaration.children.add(TreeItem("Tipo de dato: ${dataType.lexema}"))
        val identifierArray: TreeItem<String> = TreeItem<String>("Identificador: ${name.lexema}")
        declaration.children.add(identifierArray)
        val treeInitializationArray = initialization?.getTreeView()
        declaration.children.add(treeInitializationArray)

        return declaration
    }

    override fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
       symbolsTable.saveSymbolValue(name.lexema, dataType.lexema, true, ambit, name.row, name.column)
    }

}
