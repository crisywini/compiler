package co.edu.uniquindio.compilers.syntacticAnalyzer

import javafx.scene.control.TreeItem

class Decision(var logicalExpression: LogicalExpression, var statementBlock:ArrayList<Statement>, var statementBlock2: ArrayList<Statement>?) :Statement() {

    override fun toString(): String {
        return "Decision(logicalExpression=$logicalExpression, statementBlock=$statementBlock, statementBlock2=$statementBlock2)"
    }

    override fun getTreeView(): TreeItem<String> {
        var root = TreeItem("Decision")
        var condition= TreeItem("Condicion")
        condition.children.add(logicalExpression.getTreeView())
        root.children.add(condition)

        var statemenTrue= TreeItem("Sentencias Verdaderas")

        for(statement in statementBlock){
            statemenTrue.children.add(statement.getTreeView())
        }
        root.children.add(statemenTrue)

        if(statementBlock2 != null){
            var statemenFalse= TreeItem("Sentencias Falsas")

            for(s in statementBlock2 !!){
                statemenFalse.children.add(s.getTreeView())
            }
            root.children.add(statemenFalse)
        }
        return root
    }
}