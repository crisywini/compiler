package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Print(var variableName: Token?, var expression: Expression?) :Statement() {
    override fun toString(): String {
        return "Print(variableName=$variableName, expression=$expression)"
    }
    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Imprimir")

        if(variableName != null) {
            root.children.add(TreeItem("Nombre Variable:${variableName!!.lexema}"))
        }

        if(expression != null) {
            var espress= TreeItem("Expresion")
            root.children.add(expression!!.getTreeView())
        }
        return root
    }
    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {

        var symbol= symbolsTable.searchSymbolValue(variableName!!.lexema, ambit)

        if(symbol == null)
        {
            semanticErrorsList.add(Error("El campo (${variableName!!.lexema}) no existe dentro del ambito ($ambit)", variableName!!.row, variableName!!.column, ErrorCategory.ERROR_SEMANTICO))
        }else{
            if(expression != null) {
                expression!!.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
            }
        }

    }

    override fun getJavaCode(): String {
        var code= "JOptionPane.showMessageDialog(null,"
        if(expression != null){
            code+=expression!!.getJavaCode()
        }else{
            code+= variableName!!.getJavaCode()
        }
        code+=");"
        return code
    }
}