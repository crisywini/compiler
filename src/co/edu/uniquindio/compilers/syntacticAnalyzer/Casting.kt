package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Casting(): Statement(){

    var name:Token? = null
    var dataType: Token? = null
    var dataType1: Token? = null
    var expression:Expression? = null
    constructor(name: Token?, dataType:Token?, dataType1:Token?, expression:Expression?):this(){
        this.name = name
        this.dataType = dataType
        this.dataType1 = dataType1
        this.expression = expression
    }
    constructor(dataType:Token?, dataType1:Token?, expression:Expression?):this(){
        this.dataType = dataType
        this.dataType1 = dataType1
        this.expression = expression
    }
    override fun toString(): String {
        return "Casting(name=$name, dataType=$dataType, dataType=$dataType1, expression=$expression)"
    }
    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Casting")
        root.children.add(TreeItem("Tipo de dato 1: ${dataType?.lexema}"))
        root.children.add(TreeItem("Tipo de dato 2: ${dataType1?.lexema}"))
        root.children.add(expression?.getTreeView())
        return root
    }
}