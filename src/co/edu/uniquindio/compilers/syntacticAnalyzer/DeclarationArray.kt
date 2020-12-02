package co.edu.uniquindio.compilers.syntacticAnalyzer;

import co.edu.uniquindio.compilers.lexicalAnalyzer.Category
import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class DeclarationArray(var dataType:Token, var name: Token, var initialization: InitializationArray?): Statement() {
    override fun getTreeView(): TreeItem<String> {
        val declaration: TreeItem<String> = TreeItem("Declaración Arreglo")
        declaration.children.add(TreeItem("Tipo de dato: ${dataType.lexema}"))
        val identifierArray: TreeItem<String> = TreeItem<String>("Identificador: ${name.lexema}")
        declaration.children.add(identifierArray)
        val treeInitializationArray = initialization?.getTreeView()
        declaration.children.add(treeInitializationArray)

        return declaration
    }

    override fun toString(): String {
        return "DeclarationArray(dataType=$dataType, name=$name, initialization=$initialization)"
    }

    override fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
       symbolsTable.saveSymbolValue(name.lexema, dataType.lexema, true, ambit, name.row, name.column)
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {

        if(initialization != null){
            if(dataType.lexema != initialization!!.dataType.lexema) {
                semanticErrorsList.add(Error("No coincide el tipo de dato, se esperaba " + dataType.lexema, name.row, name.column, ErrorCategory.ERROR_SEMANTICO))
            } else {
                val name2 = initialization!!.name
                if(name2.category == Category.IDENTIFICADOR){
                    val symbol = symbolsTable.searchSymbolValue(name2.lexema, ambit)
                    if(symbol != null) {
                        if (symbol?.type != dataType.lexema) {
                            semanticErrorsList.add(Error("No coincide el tipo de dato, se esperaba " + dataType.lexema, name.row, name.column, ErrorCategory.ERROR_SEMANTICO))
                        }
                    } else {
                        semanticErrorsList.add(Error("La variable ${name2.lexema} no existe", name.row, name.column, ErrorCategory.ERROR_SEMANTICO))
                    }
                } else {
                    if(name2.category != Category.ENTERO){
                        semanticErrorsList.add(Error("Se esperaba un numero entero", name.row, name.column, ErrorCategory.ERROR_SEMANTICO))
                    }
                }
            }
        }
    }

    override fun getJavaCode(): String {
        var code= dataType.getJavaCode() + "[]" + name.getJavaCode()

        if(initialization != null){
            code+= "= ["
            code += initialization!!.getJavaCode()
            code+= "]"
        }
        code+= ";"
        return code
    }
}
