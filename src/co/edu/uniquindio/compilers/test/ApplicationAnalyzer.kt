package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.syntacticAnalyzer.SyntacticAnalyzer

fun main(){
    // <VariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
    val lexico = LexicalAnalyzer("becu tutti ; ~hola_~hola2\\ tutti ~isPrime[]<>becu")
    lexico.analyze()
    //print(lexico.tokenList)
    val sintactico = SyntacticAnalyzer(lexico.tokenList)
    println(sintactico.isCompilationUnit())
    println(sintactico.errorList)
}