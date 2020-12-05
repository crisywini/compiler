package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Return() :Statement(){

    var identifier:Token? = null
    var expression:Expression? = null
    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }
    constructor(expression:Expression?):this(){
        this.expression = expression
    }
    override fun toString(): String {
        return "Return(variableName=$identifier, expression=$expression)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Return")

        if(identifier != null) {
            root.children.add(TreeItem("Identificador:${identifier!!.lexema}"))
        }
        return root
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        var symbol= symbolsTable.searchSymbolValue(identifier!!.lexema, ambit)

        if(symbol == null)
        {
            semanticErrorsList.add(Error("El campo (${identifier!!.lexema}) no existe dentro del ambito ($ambit)", identifier!!.row, identifier!!.column, ErrorCategory.ERROR_SEMANTICO))
        }else{
            if(expression != null) {
                expression!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
            }
            /* falta comprobar que el retorno sea del mismo tipo de la funcion a la que pertenece */
        }

    }

    override fun getJavaCode(): String {
        var code="return "
        if(identifier !=null){
            code+= identifier!!.getJavaCode()
        }else if(expression!=null){
            code+= expression!!.getJavaCode()
        }
        code+=";"
        return code
    }
}
