package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Param(var name:Token, var dataType:Token) {
    override fun toString(): String {
        return "Param(name=$name, dataType=$dataType)"
    }
}