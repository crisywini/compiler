package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class RelationalExpression():Expression() {

    var expression1:Expression? = null
    var expression2:Expression? = null
    var operator1:Token? = null
    var operator2:Token? = null
    var token:Token? = null
    var relationalExpression:RelationalExpression? = null

    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Expresión Relacional")

        if(expression1 != null){
            root.children.add(expression1?.getTreeView())
        }
        if(expression2 != null){
            root.children.add(expression2?.getTreeView())
        }

        if(operator1!=null) {
            root.children.add(TreeItem("Operador : ${operator1?.lexema}"))
        }
        if(operator2 != null){
            root.children.add(TreeItem("Operador : ${operator2?.lexema}"))
        }
        if(token != null){
            root.children.add(TreeItem("Token : ${token?.lexema}"))
        }
        if(relationalExpression != null){
            root.children.add(relationalExpression?.getTreeView())
        }
        return root
    }
    constructor(expression1: Expression?, operator1:Token?, expression2: Expression?):this(){
        this.expression1 = expression1
        this.operator1 = operator1
        this.expression2 = expression2
    }
    constructor(token:Token?):this(){
        this.token = token
    }

    constructor(expression1: Expression?):this(){
        this.expression1 = expression1
    }

}