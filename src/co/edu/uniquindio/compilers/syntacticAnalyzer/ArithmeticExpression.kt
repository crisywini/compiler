package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Category
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class ArithmeticExpression():Expression() {

    var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator:Token? = null
    var numericValue:NumericValue? = null
    var identifier:Token? = null

    constructor(arithmeticExpression1: ArithmeticExpression?, operator:Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
        this.arithmeticExpression2 = arithmeticExpression2
        this.operator = operator
    }

    constructor(arithmeticExpression1: ArithmeticExpression?):this(){
        this.arithmeticExpression1 = arithmeticExpression1
    }

    constructor(numericValue: NumericValue?, operator: Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.numericValue = numericValue
        this.operator = operator
        this.arithmeticExpression2 = arithmeticExpression2
    }
    constructor(identifier: Token?, operator: Token?, arithmeticExpression2: ArithmeticExpression?):this(){
        this.identifier = identifier
        this.operator = operator
        this.arithmeticExpression2 = arithmeticExpression2
    }

    constructor(numericValue: NumericValue?):this(){
        this.numericValue = numericValue
    }
    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }

    /**
     *     var arithmeticExpression1:ArithmeticExpression? = null
    var arithmeticExpression2:ArithmeticExpression? = null
    var operator:Token? = null
    var numericValue:NumericValue? = null
    var identifier:Token? = null
     */
    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Expresión aritmética")
        if(arithmeticExpression1 != null){
            root.children.add(arithmeticExpression1?.getTreeView())
        }
        if(arithmeticExpression2 != null){
            root.children.add(arithmeticExpression2?.getTreeView())
        }
        if(operator != null){
            root.children.add(TreeItem("Operador aritmético: ${operator?.lexema}"))
        }
        if(identifier != null){
            root.children.add(TreeItem("Operador aritmético: ${identifier?.lexema}"))
        }
        return root
    }

    override fun getType(symbolTable: SymbolsTable, ambit: String): String {
      if (arithmeticExpression1 != null && arithmeticExpression2 != null){

          var type1 = arithmeticExpression1!!.getType(symbolTable, ambit)
          var type2 = arithmeticExpression2!!.getType(symbolTable, ambit)

          if(type1 == "demol" || type2 == "bemol"){
              return "bemol"
          }

        } else if (arithmeticExpression1 != null){
          return arithmeticExpression1!!.getType(symbolTable, ambit)

        } else if (numericValue != null && arithmeticExpression2 != null){

        } else if ( numericValue != null){
            if(numericValue!!.term!!.category == Category.ENTERO){
                return "becu"
            } else if(numericValue!!.term!!.category == Category.DECIMAL){
                return "bemol"
            } else {
                val symbol = symbolTable.searchSymbolValue(numericValue!!.term!!.lexema, ambit)
                if(symbol != null){
                    return  symbol.type
                }
            }
        } else if ( identifier != null ){

      }
        return ""
    }
}