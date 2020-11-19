package co.edu.uniquindio.compilers.syntacticAnalyzer

class Cycle(var logicalExpression: LogicalExpression, var statementBlock:ArrayList<Statement>) :Statement() {
    override fun toString(): String {
        return "Cycle(logicalExpression=$logicalExpression, statementBlock=$statementBlock)"
    }
}