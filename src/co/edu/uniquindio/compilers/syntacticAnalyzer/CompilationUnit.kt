package co.edu.uniquindio.compilers.syntacticAnalyzer

class CompilationUnit(var functionsList:ArrayList<Function>) {
    override fun toString(): String {
        return "CompilationUnit(functionsList=$functionsList)"
    }
}