package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.syntacticAnalyzer.SyntacticAnalyzer

fun main(){
    // <VariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
    val lexico = LexicalAnalyzer("becu solo ; ~hola:#12 \\ tutti ~isPrime[]<>becu")
    lexico.analyze()
    //print(lexico.tokenList)
    val sintactico = SyntacticAnalyzer(lexico.tokenList)
    //print(sintactico.isParam())
    println(sintactico.isCompilationUnit())
    //println(sintactico.isDecision())
   // println(sintactico.errorList)
}