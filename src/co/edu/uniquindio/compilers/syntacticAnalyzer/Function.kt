package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Function(var functionName:Token,var returnType:Token,var paramList:ArrayList<Param>,var statementBlock:ArrayList<Statement>?) {
    override fun toString(): String {
        return "Function(functionName=$functionName, returnType=$returnType, paramList=$paramList, statementBlock=$statementBlock)"
    }
}