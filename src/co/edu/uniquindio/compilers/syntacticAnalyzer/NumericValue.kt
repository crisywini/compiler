package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class NumericValue(var sign:Token?, var term:Token?) {

    fun getJavaCode(): String {
        if (sign != null) {
            return sign!!.getJavaCode()+ " "+ term!!.getJavaCode()
        }
        else{
           return term!!.getJavaCode()
        }
    }
}