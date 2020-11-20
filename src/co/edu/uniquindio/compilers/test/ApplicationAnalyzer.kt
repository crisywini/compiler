package co.edu.uniquindio.compilers.test

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.syntacticAnalyzer.SyntacticAnalyzer

fun main(){
    // <VariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
    // [~h] <: [~j] <- [~n] >: [$12,5] or [~a]
    val sourceCode = "[#12] <- #45"

    val lexico = LexicalAnalyzer(sourceCode)
    lexico.analyze()
    //print(lexico.tokenList)
    println("LISTA DE TOKENS LEXICO\n${lexico.tokenList}")
    val sintactico = SyntacticAnalyzer(lexico.tokenList)
    println(sintactico.isLogicalExpression())
  // println("ES EXPRESION :\n${sintactico.isLogicalExpression()}")
   //println(sintactico.isFunction())
   //println(sintactico.isCycle())
   println("LISTA DE ERORES\n${sintactico.errorList}")
}