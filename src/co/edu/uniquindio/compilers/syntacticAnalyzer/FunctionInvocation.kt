package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class FunctionInvocation(var functionName: Token, var argumentList:ArrayList<Argument>?) :Statement() {
    override fun toString(): String {
        return "FunctionInvocation(functionName=$functionName, argumentList=$argumentList)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Invocar Funcion")
        root.children.add(TreeItem("Variable:${functionName.lexema}"))

        if(argumentList != null){
            var argumet= TreeItem("Argumentos")

            for(s in argumentList !!){
                argumet.children.add(s.getTreeView())
            }
            root.children.add(argumet)
        }

        return root
    }

    fun getTypeArguments(symbolsTable: SymbolsTable,semanticErrorsList: ArrayList<Error>, ambit: String): ArrayList<String>{

        var listArgs= ArrayList<String>()
        for( a in  argumentList!!){
            listArgs.add(a.getType(symbolsTable,semanticErrorsList,ambit))
        }
        return listArgs
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        var listTypeArgs= getTypeArguments(symbolsTable,semanticErrorsList,ambit)
        var funtion= symbolsTable.searchSymbolFunction(functionName.lexema,listTypeArgs)

        if(funtion==null)
        {
            semanticErrorsList.add(Error("La funcion (${functionName.lexema}) $listTypeArgs. no existe ", functionName.row, functionName.column, ErrorCategory.ERROR_SEMANTICO))
        }
    }

    override fun getJavaCode (): String {
        var code= functionName.getJavaCode()+"("

        if(argumentList!!.isNotEmpty()) {
            for (a in argumentList!!) {
                code += a.getJavaCode() + ","
            }
            code = code.substring(0, code.length - 1)
        }
        code+=");"
        return code
    }
}