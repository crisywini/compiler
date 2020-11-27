package co.edu.uniquindio.compilers.semanticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.syntacticAnalyzer.CompilationUnit

class SemanticAnalyzer(var compilationUnit: CompilationUnit) {
    var semanticErrorsList:ArrayList<Error> = ArrayList()
    var symbolsTable:SymbolsTable = SymbolsTable(semanticErrorsList)

    fun fillTableSymbols(){

     //   compilationUnit.fillTableSymbols(symbolsTable, semanticErrorsList)
    }

    fun analyzeSemantic(){

       // compilationUnit.analyzeSemantic(symbolsTable, semanticErrorsList)
    }
}