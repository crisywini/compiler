package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class ArithmeticExpression():Expression() {

    var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator:Token? = null
    var numericValue:NumericValue? = null
    var identifier:Token? = null

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
    constructor(identifier: Token?, operator: Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.identifier = identifier
        this.operator = operator
        this.arithmeticExpression2 = arithmeticExpression2
    }


    constructor(numericValue: NumericValue?):this(){
        this.numericValue = numericValue
    }
    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }

}