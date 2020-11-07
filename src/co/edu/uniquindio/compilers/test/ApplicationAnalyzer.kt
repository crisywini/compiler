package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.syntacticAnalyzer.SyntacticAnalyzer

fun main(){
    val lexico = LexicalAnalyzer("tutti ~isPrime[]<>becu ")
    lexico.analyze()
    //print(lexico.tokenList)
    val sintactico = SyntacticAnalyzer(lexico.tokenList)
    println(sintactico.isCompilationUnit())
    println(sintactico.errorList)
}