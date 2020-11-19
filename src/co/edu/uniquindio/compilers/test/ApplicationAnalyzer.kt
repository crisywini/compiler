package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.syntacticAnalyzer.SyntacticAnalyzer

fun main(){
    // <VariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
    val lexico = LexicalAnalyzer(" tutti ~isPrime[]< rondo [ 5 or 7 ]  < bemol tutti ; ~hola\\ > >becu   ")
    lexico.analyze()
    //print(lexico.tokenList)
    val sintactico = SyntacticAnalyzer(lexico.tokenList)
    //print(sintactico.isParam())
   println(sintactico.isCompilationUnit())
   //println(sintactico.isFunction())
   // println(sintactico.isCycle())
   println(sintactico.errorList)
}