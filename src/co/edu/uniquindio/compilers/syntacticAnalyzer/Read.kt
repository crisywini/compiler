package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Read(var variableName: Token) :Statement(){
    override fun toString(): String {
        return "Read(variableName=$variableName)"
    }
}