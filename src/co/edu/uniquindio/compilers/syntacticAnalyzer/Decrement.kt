package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Decrement (var variableName: Token):Statement(){
    override fun toString(): String {
        return "Decrement(variableName=$variableName)"
    }
}