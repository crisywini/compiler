package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Return() :Statement(){

    var identifier:Token? = null
    var expression:Expression? = null
    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }
    constructor(expression:Expression?):this(){
        this.expression = expression
    }
    override fun toString(): String {
        return "Return(variableName=$identifier, expression=$expression)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Return")

        if(identifier != null) {
            root.children.add(TreeItem("Identificador:${identifier!!.lexema}"))
        }
        return root
    }
}
