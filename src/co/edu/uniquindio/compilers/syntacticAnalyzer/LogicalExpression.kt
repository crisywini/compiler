package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import co.edu.uniquindio.compilers.test.operate
import javafx.scene.control.TreeItem

class LogicalExpression():Expression() {
    var relationalExpression1:RelationalExpression? = null
    var relationalExpression2:RelationalExpression? = null
    var logicalOperator1:Token? = null

    constructor(relationalExpression1: RelationalExpression?, logicalOperator1: Token?, relationalExpression2: RelationalExpression?):this(){
        this.relationalExpression1 = relationalExpression1
        this.logicalOperator1 = logicalOperator1
        this.relationalExpression2 = relationalExpression2
    }
    constructor(relationalExpression1: RelationalExpression?):this(){
        this.relationalExpression1 = relationalExpression1
    }
    constructor(logicalOperator1:Token?,relationalExpression1: RelationalExpression?):this(){
        this.logicalOperator1 = logicalOperator1
        this.relationalExpression1 = relationalExpression1
    }

    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Expresión Lógica")
        if(relationalExpression1 !=null){
            root.children.add(relationalExpression1?.getTreeView())
        }
        if(logicalOperator1 != null){
            root.children.add(TreeItem("Operador: ${logicalOperator1?.lexema}"))
        }
        if(relationalExpression2 != null){
            root.children.add(relationalExpression2?.getTreeView())
        }
        return root
    }

    override fun getType(symbolTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String): String {
        return "pulso"
    }

    override fun getJavaCode(): String {
        if(relationalExpression1 != null && relationalExpression2 != null){
            return relationalExpression1!!.getJavaCode() + logicalOperator1!!.getJavaCode() + relationalExpression2!!.getJavaCode()
        }else if(logicalOperator1 != null && relationalExpression1 != null && relationalExpression2 == null){
            return logicalOperator1!!.getJavaCode()+ relationalExpression1!!.getJavaCode()
        }else{
            return relationalExpression1!!.getJavaCode()
        }
    }

}