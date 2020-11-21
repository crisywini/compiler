package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class StringExpression() :Expression(){
    var string:Token? = null
    var stringExpression:StringExpression? = null
    constructor(string:Token?, stringExpression: StringExpression?):this(){
        this.string = string
        this.stringExpression = stringExpression
    }
    constructor(string: Token?):this(){
        this.string = string
    }

    override fun toString(): String {
        return "StringExpression(string=$string, stringExpression=$stringExpression)"
    }

    override fun getTreeView(): TreeItem<String> {
       val root:TreeItem<String> = TreeItem("Expresión Cadena")

        root.children.add(TreeItem("Cadena: $string"))
        if(stringExpression != null){
            root.children.add(stringExpression?.getTreeView())
        }
        return root
    }
}