package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
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
        symbolsTable.saveSymbolFunction(functionName.lexema, returnType.lexema, getParamTypes(), ambit, functionName.row, functionName.column)
        for (param in paramList) {
            symbolsTable.saveSymbolValue(param.name.lexema, param.dataType.lexema, true, functionName.lexema, param.name.row, param.name.column)
        }
        for (statement in statementBlock) {
            statement.fillTableSymbols(symbolsTable, semanticErrorsList, functionName.lexema)
        }
    }

    fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>) {
        for (statement in statementBlock) {
            statement.analyzeSemantic(symbolsTable, semanticErrorsList, functionName.lexema)
        }
    }

    fun getJavaCode (): String {

        var code=""
        if(functionName.lexema=="Principal"){
            code= "public static void main (String[] args) {"

        }else{
            code = "static" + returnType.getJavaCode() +" "+ functionName.getJavaCode()+" ("
            for(p in paramList){
                code+= p.getJavaCode()+","
            }
            code=code.substring(0,code.length-1)
            code+=") {"
        }
        for(s in statementBlock){
            code+= s.getJavaCode()
        }
        code+="}"
        return code

    }
}