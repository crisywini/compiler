package co.edu.uniquindio.compilers.syntacticAnalyzer

import javafx.scene.control.TreeItem

class Cycle(var logicalExpression: LogicalExpression, var statementBlock:ArrayList<Statement>) :Statement() {
    override fun toString(): String {
        return "Cycle(logicalExpression=$logicalExpression, statementBlock=$statementBlock)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Ciclo while")
        var condition= TreeItem("Condicion")
        condition.children.add(logicalExpression.getTreeView())
        root.children.add(condition)

        var statemenRoot= TreeItem("Sentencias")

        for(statement in statementBlock){
            statemenRoot.children.add(statement.getTreeView())
        }

        root.children.add(statemenRoot)
        return root
    }
}