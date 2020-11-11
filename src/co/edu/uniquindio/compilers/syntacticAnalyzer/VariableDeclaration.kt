package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class VariableDeclaration(var dataType:Token, var identifierList:ArrayList<Token>) {
    override fun toString(): String {
        return "VariableDeclaration(dataType=$dataType, identifierList=$identifierList)"
    }
}