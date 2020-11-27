package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class ArithmeticExpression():Expression() {

    var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator:Token? = null
    var numericValue:NumericValue? = null

    constructor(arithmeticExpression1: ArithmeticExpression?, operator:Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
        this.arithmeticExpression2 = arithmeticExpression2
        this.operator = operator
    }

    constructor(arithmeticExpression1: ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
    }

    constructor(numericValue: NumericValue?, operator: Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.numericValue = numericValue
        this.operator = operator
        this.arithmeticExpression2 = arithmeticExpression2
    }


    constructor(numericValue: NumericValue?):this(){
        this.numericValue = numericValue
    }

    /**
     *     var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator:Token? = null
    var numericValue:NumericValue? = null
    var identifier:Token? = null
     */
    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Expresión aritmética")
        if(arithmeticExpression1 != null){
            root.children.add(arithmeticExpression1?.getTreeView())
        }
        if(arithmeticExpression2 != null){
            root.children.add(arithmeticExpression2?.getTreeView())
        }
        if(operator != null){
            root.children.add(TreeItem("Operador aritmético: ${operator?.lexema}"))
        }
        if(numericValue != null){
            if(numericValue?.sign != null){
                root.children.add(TreeItem("Valor: ${numericValue?.sign?.lexema} ${numericValue?.term?.lexema}"))
            }else {
                root.children.add(TreeItem("Valor: ${numericValue?.term?.lexema}"))
            }
        }
        return root
    }

    override fun toString(): String {
        return "ArithmeticExpression(arithmeticExpression1=$arithmeticExpression1, arithmeticExpression2=$arithmeticExpression2, operator=$operator, numericValue=$numericValue)"
    }

}