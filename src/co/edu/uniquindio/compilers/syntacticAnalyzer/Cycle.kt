package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
import javafx.scene.control.TreeItem

class Cycle(var logicalExpression: LogicalExpression, var statementBlock: ArrayList<Statement>) : Statement() {
    override fun toString(): String {
        return "Cycle(logicalExpression=$logicalExpression, statementBlock=$statementBlock)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Ciclo while")
        var condition = TreeItem("Condicion")
        condition.children.add(logicalExpression.getTreeView())
        root.children.add(condition)

        var statemenRoot = TreeItem("Sentencias")

        for (statement in statementBlock) {
            statemenRoot.children.add(statement.getTreeView())
        }

        root.children.add(statemenRoot)
        return root
    }

    override fun fillTableSymbols(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
        for(statement in statementBlock){
            statement.fillTableSymbols(symbolsTable, semanticErrorsList, ambit)
        }
    }

    override fun analyzeSemantic(symbolsTable: SymbolsTable, semanticErrorsList: ArrayList<Error>, ambit: String) {
       logicalExpression.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
        for (statement in statementBlock){
            statement.analyzeSemantic(symbolsTable,semanticErrorsList,ambit)
        }
    }

    override fun getJavaCode(): String {
        var code= "while (" + logicalExpression.getJavaCode()+") {"
        for (statement in statementBlock){
            code+= statement.getJavaCode()
        }
        code+="}"
        return code
    }
}