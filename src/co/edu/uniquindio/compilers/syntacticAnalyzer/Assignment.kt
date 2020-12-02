package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Assignment(var identifier:Token, var expression:Expression):Statement() {
    override fun toString(): String {
        return "Assignment(identifier=$identifier, expression=$expression)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Asignacion")
        root.children.add(TreeItem("Variable:${identifier.lexema}"))
        root.children.add(expression.getTreeView())

        return root
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {

        var symbol= symbolsTable.searchSymbolValue(identifier.lexema, ambit)

        if(symbol == null)
        {
            semanticErrorsList.add(Error("El campo ${identifier.lexema} no existe dentro del ambito $ambit", identifier.row, identifier.column, ErrorCategory.ERROR_SEMANTICO))
        }else{
            var type= symbol.type
            if(expression != null)
            {
                expression!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
                var typeExpresssion= expression.getType(symbolsTable,semanticErrorsList,ambit)
                if(typeExpresssion != type)
                {
                    semanticErrorsList.add(Error("El tipo de dato de la expresion o identificador es ($typeExpresssion) y no coincide con el tipo de dato del campo (${identifier.lexema}) que es de tipo ($type)", identifier.row, identifier.column, ErrorCategory.ERROR_SEMANTICO))
                }

            }
        }

    }
}