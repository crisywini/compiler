package co.edu.uniquindio.compilers.syntacticAnalyzer

class Decision(var logicalExpression: LogicalExpression, var statementBlock:ArrayList<Statement>, var statementBlock2: ArrayList<Statement>?) :Statement() {

    override fun toString(): String {
        return "Decision(logicalExpression=$logicalExpression, statementBlock=$statementBlock, statementBlock2=$statementBlock2)"
    }
}