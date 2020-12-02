package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class RelationalExpression():Expression() {

    var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator1:Token? = null
    var operator2:Token? = null
    var token:Token? = null
    var relationalExpression:RelationalExpression? = null
    var expression:Expression? = null

    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Expresión Relacional")

        if(arithmeticExpression1 != null){
            root.children.add(arithmeticExpression1?.getTreeView())
        }
        if(arithmeticExpression2 != null){
            root.children.add(arithmeticExpression2?.getTreeView())
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
    constructor(arithmeticExpression1:ArithmeticExpression?, operator1:Token?, arithmeticExpression2:ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
        this.operator1 = operator1
        this.arithmeticExpression2 = arithmeticExpression2
    }
    constructor(token:Token?):this(){
        this.token = token
    }

    constructor(expression: Expression?):this(){
        this.expression = expression
    }

    override fun toString(): String {
        return "RelationalExpression(arithmeticExpression1=$arithmeticExpression1, arithmeticExpression2=$arithmeticExpression2, operator1=$operator1, operator2=$operator2, token=$token, relationalExpression=$relationalExpression, expression=$expression)"
    }

    override fun getType(symbolTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String): String {
        return "pulso"
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        if(arithmeticExpression1 != null && arithmeticExpression2 != null)
        {
            arithmeticExpression1!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
            arithmeticExpression2!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)

        }else if(expression != null) {
            expression!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
        }
    }
}