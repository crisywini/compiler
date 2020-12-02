package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Param(var name:Token, var dataType:Token) {
    override fun toString(): String {
        return "Param(name=$name, dataType=$dataType)"
    }
    fun getTreeView():TreeItem<String>{
        return TreeItem("${name.lexema} : ${dataType.lexema}")
    }
    fun getJavaCode (): String{
        return dataType.lexema+" "+ name.lexema
    }
}