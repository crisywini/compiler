package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class FunctionInvocation(var functionName: Token, var argumentList:ArrayList<Argument>?) :Statement() {
    override fun toString(): String {
        return "FunctionInvocation(functionName=$functionName, argumentList=$argumentList)"
    }
}