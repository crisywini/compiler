package co.edu.uniquindio.compilers.semanticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory

class SymbolsTable(var errorList:ArrayList<Error>) {

    var symbolsList:ArrayList<Symbol> = ArrayList()

    /**
     * Allows you to save a symbol that represents a variable, a constant, a parameter or an array
     */
    fun saveSymbolValue(name:String, dataType:String, modifiable:Boolean, ambit:String, row:Int, column:Int){
        var symbol = searchSymbolValue(name, ambit)

        if(symbol==null){
            symbolsList.add(Symbol(name, dataType, modifiable, ambit, row, column))
        } else {
            errorList.add( Error("El campo $name ya existe dentro del ámbito $ambit", row, column, ErrorCategory.ERROR_SEMANTICO) )
        }
    }

    /**
     * Allows to save a symbol that represents a function (method)
     */
    fun saveSymbolFunction(name:String, returnType:String, paramTypes:ArrayList<String>, ambit:String, row: Int, column: Int){
        val symbol = searchSymbolFunction(name, paramTypes)

        if(symbol==null){
            symbolsList.add(Symbol(name, returnType, paramTypes, ambit))
        } else {
            errorList.add( Error("La función $name ya existe dentro del ámbito $ambit", row, column, ErrorCategory.ERROR_SEMANTICO ) )
        }
    }

    /**
     * Allows searching for a Value within the symbol table
     */
    fun searchSymbolValue(name:String, ambit:String):Symbol?{
        for(symbol in symbolsList) {
            if (symbol.paramTypes == null) {
                if (symbol.name == name && symbol.ambit == ambit) {
                    return symbol
                }
            }
        }
        return null
    }

    /**
     * Allows searching for a Function within the symbol table
     */
    fun searchSymbolFunction(name:String, paramTypes: ArrayList<String>):Symbol?{
        for(symbol in symbolsList) {
            if (symbol.paramTypes != null) {
                if (symbol.name == name && symbol.paramTypes == paramTypes) {
                    return symbol
                }
            }
        }
        return null
    }
}