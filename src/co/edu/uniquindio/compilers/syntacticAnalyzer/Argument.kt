package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
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
     fun getType(symbolsTable: SymbolsTable,semanticErrorsList: ArrayList<Error>, ambit: String):String{

        var symbol= symbolsTable.searchSymbolValue(identifier!!.lexema, ambit)

        if(arithmeticExpression != null){
            var typeExpresssion= arithmeticExpression!!.getType(symbolsTable,semanticErrorsList,ambit)
            return typeExpresssion
        }else  if(symbol == null) {
            semanticErrorsList.add(Error("El campo ${identifier!!.lexema} no existe dentro del ambito $ambit", identifier!!.row, identifier!!.column, ErrorCategory.ERROR_SEMANTICO))
        }else{
            var type= symbol.type
            return type
        }
        return ""
    }
    fun getJavaCode (): String {

        if(arithmeticExpression!= null){
            return arithmeticExpression!!.getJavaCode()
        }else{
            return identifier!!.getJavaCode()
        }
    }

}