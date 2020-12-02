package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Argument(){

    var arithmeticExpression:ArithmeticExpression? = null
    var identifier:Token? = null
    constructor(identifier:Token?, arithmeticExpression: ArithmeticExpression?):this(){
        this.identifier = identifier
        this.arithmeticExpression = arithmeticExpression
    }
    constructor(arithmeticExpression: ArithmeticExpression?):this(){
        this.arithmeticExpression = arithmeticExpression
    }

    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }
    fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Argumento")
        if(identifier != null){
            root.children.add(TreeItem("Identificador: ${identifier?.lexema}"))
        }
        if(arithmeticExpression !=null){
            root.children.add(arithmeticExpression?.getTreeView())
        }
        return root
    }
    open fun getType(symbolsTable: SymbolsTable,ambit: String):String{
        return ""
    }

}