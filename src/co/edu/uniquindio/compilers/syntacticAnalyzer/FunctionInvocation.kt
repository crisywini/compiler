package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
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
}