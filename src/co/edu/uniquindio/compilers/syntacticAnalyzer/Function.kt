package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Function(var functionName:Token,var returnType:Token,var paramList:ArrayList<Param>,var statementBlock:ArrayList<Statement>) {
    override fun toString(): String {
        return "Function(functionName=$functionName, returnType=$returnType, paramList=$paramList, statementBlock=$statementBlock)"
    }

    fun getTreeView():TreeItem<String>{
        val root = TreeItem("Función")
        root.children.add(TreeItem("Nombre:${functionName.lexema}"))
        root.children.add(TreeItem("Tipo Retorno:${returnType.lexema}"))
        val paramRoot:TreeItem<String> = TreeItem("Parámetros")

        for(param in paramList){
            paramRoot.children.add(param.getTreeView())
        }
        root.children.add(paramRoot)

        val statementRoot:TreeItem<String> = TreeItem("Sentencias")

        for(statement in statementBlock){
            statementRoot.children.add(statement.getTreeView())
        }
        root.children.add(statementRoot)

        return root
    }
}