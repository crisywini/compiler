package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Casting(var name: Token?, var dataType:Token, var dataType1:Token, var expression:Expression?): Statement(){

    override fun toString(): String {
        return "Casting(name=$name, dataType=$dataType, dataType=$dataType1, expression=$expression)"
    }
    override fun getTreeView(): TreeItem<String> {
        return TreeItem("${name?.lexema} : ${dataType.lexema} : ${dataType1.lexema} : $expression")
    }
}