package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class InitializationArray (var name: Token, var dataType: Token){
    override fun toString(): String {
        return "declarationArray(dataType=$dataType, name=$name)"
    }
    fun getTreeView(): TreeItem<String> {
        val declaration: TreeItem<String> = TreeItem("Asignación Arreglo")
        declaration.children.add(TreeItem("Tipo de dato: ${dataType.lexema}"))
        val identifierArray: TreeItem<String> = TreeItem<String>("Tamaño: ${name.lexema}")
        declaration.children.add(identifierArray)

        return declaration
    }


}