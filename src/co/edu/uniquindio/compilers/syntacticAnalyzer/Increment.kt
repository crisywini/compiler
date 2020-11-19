package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Increment(var variableName: Token):Statement(){
    override fun toString(): String {
        return "Increment(variableName=$variableName)"
    }
}