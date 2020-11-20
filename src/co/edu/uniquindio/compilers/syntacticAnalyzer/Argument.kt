package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Argument(var name: Token, var expression: Expression?){
    override fun toString(): String {
        return "Argument(name=$name, expression=$expression)"
    }
    fun getTreeView(): TreeItem<String> {
        return TreeItem("${name.lexema} : ${expression.toString()}")
    }

}