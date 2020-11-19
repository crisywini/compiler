package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Print(var variableName: Token?, var expression: Expression?) :Statement() {
    override fun toString(): String {
        return "Print(variableName=$variableName, expression=$expression)"
    }
}