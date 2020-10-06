package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer

fun main(){
    val lexico = LexicalAnalyzer("           4654.64    asd asd321 654\nasdasdasd789654")
    lexico.analyze()
    print(lexico.tokenList)
}