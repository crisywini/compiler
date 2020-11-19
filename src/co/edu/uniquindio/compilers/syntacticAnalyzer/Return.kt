package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Return(var variableName: Token?, var expression: Expression?) :Statement(){
    override fun toString(): String {
        return "Return(variableName=$variableName, expression=$expression)"
    }
}
