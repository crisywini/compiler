package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class RelationalExpression():Expression() {

    var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator1:Token? = null
    var operator2:Token? = null
    var relationalExpression:RelationalExpression? = null

    constructor(arithmeticExpression1:ArithmeticExpression?, operator1:Token?, arithmeticExpression2:ArithmeticExpression?, operator2:Token?, relationalExpression:RelationalExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
        this.operator1 = operator1
        this.arithmeticExpression2 = arithmeticExpression2
        this.operator2 = operator2
        this.relationalExpression = relationalExpression
    }
    constructor(arithmeticExpression1:ArithmeticExpression?, operator1:Token?, arithmeticExpression2:ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
        this.operator1 = operator1
        this.arithmeticExpression2 = arithmeticExpression2
    }

}