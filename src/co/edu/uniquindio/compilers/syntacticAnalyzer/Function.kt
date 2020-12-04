package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Function(var functionName: Token, var returnType: Token, var paramList: ArrayList<Param>, var statementBlock: ArrayList<Statement>) {
    override fun toString(): String {
        return "Function(functionName=$functionName, returnType=$returnType, paramList=$paramList, statementBlock=$statementBlock)"
    }

    fun getTreeView(): TreeItem<String> {
        val root = TreeItem("Función")
        root.children.add(TreeItem("Nombre:${functionName.lexema}"))
        root.children.add(TreeItem("Tipo Retorno:${returnType.lexema}"))
        val paramRoot: TreeItem<String> = TreeItem("Parámetros")

        for (param in paramList) {
            paramRoot.children.add(param.getTreeView())
        }
        root.children.add(paramRoot)

        val statementRoot: TreeItem<String> = TreeItem("Sentencias")

        for (statement in statementBlock) {
            statementRoot.children.add(statement.getTreeView())
        }
        root.children.add(statementRoot)

        return root
    }

    fun getParamTypes(): ArrayList<String> {
        var list = ArrayList<String>()
        for (param in paramList) {
            list.add(param.dataType.lexema)
        }
        return list
    }

    fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {

        var paramLi=ArrayList<String>()
        for(param in paramList){
            paramLi.add(param.dataType.lexema)
        }
        var symbol=symbolsTable.searchSymbolFunction(functionName.lexema,paramLi)
        if(symbol ==null) {

            symbolsTable.saveSymbolFunction(functionName.lexema, returnType.lexema, getParamTypes(), ambit, functionName.row, functionName.column)
            for (param in paramList) {
                symbolsTable.saveSymbolValue(param.name.lexema, param.dataType.lexema, true, functionName.lexema, param.name.row, param.name.column)
            }
            for (statement in statementBlock) {
                statement.fillTableSymbols(symbolsTable, semanticErrorsList, functionName.lexema)
            }
        }else{
            semanticErrorsList.add(Error("La funcion ${functionName.lexema} ya existe ", functionName.row, functionName.column, ErrorCategory.ERROR_SEMANTICO))
        }
    }

    fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>) {
        var flag=false
        for (statement in statementBlock) {
            statement.analyzeSemantic(symbolsTable, semanticErrorsList, functionName.lexema)
            if(statement is Return) {
                if (returnType.lexema != "dacapo") {
                    flag = true
                    statement as Return
                    var ident = statement.identifier
                    if (ident == null) {
                        if (returnType.lexema != statement.expression!!.getType(symbolsTable, semanticErrorsList, functionName.lexema)) {
                            semanticErrorsList.add(Error("El tipo de dato de la funcion $functionName no coincide con el tipo de dato del retorno  ", returnType.row, returnType.column, ErrorCategory.ERROR_SEMANTICO))

                        }
                    } else {
                        var symbol = symbolsTable.searchSymbolValue(ident!!.lexema, functionName.lexema)
                        if (symbol == null) {
                            semanticErrorsList.add(Error("La variable ${ident.lexema} no esta declarada ", ident.row, ident.column, ErrorCategory.ERROR_SEMANTICO))
                        } else if (returnType.lexema != symbol.type) {
                            semanticErrorsList.add(Error("El tipo de dato de la funcion ${functionName.lexema} no coincide con el tipo de dato del retorno  ", returnType.row, returnType.column, ErrorCategory.ERROR_SEMANTICO))

                        }
                    }
                }else{
                    semanticErrorsList.add(Error("La funcion no puede retornar ", returnType.row, returnType.column, ErrorCategory.ERROR_SEMANTICO))
                }
            }
        }
        if(flag==false && returnType.lexema != "dacapo"){
            semanticErrorsList.add(Error("No existe ningun retorno ", returnType.row, returnType.column, ErrorCategory.ERROR_SEMANTICO))
        }
    }

    fun getJavaCode (): String {

        var code=""
        if(functionName.lexema=="~Principal"){
            code= "public static void main (String[] args) {"

        }else{
            code = "public static " + returnType.getJavaCode() +" "+ functionName.getJavaCode()+" ("

            if(paramList.isNotEmpty()) {
                for (p in paramList) {
                    code += p.getJavaCode() + ","
                }
                code = code.substring(0, code.length - 1)
            }
            code+=") {"
        }
        for(s in statementBlock){
            code+= s.getJavaCode()
        }
        code+="}"
        return code

    }
}
