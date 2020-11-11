package co.edu.uniquindio.compilers.syntacticAnalyzer

class CompilationUnit(var functionsList:ArrayList<Function>, var variableDeclarationList:ArrayList<VariableDeclaration>) {
    override fun toString(): String {
        return "CompilationUnit(variableDeclarationList=$variableDeclarationList,functionsList=$functionsList )"
    }
}