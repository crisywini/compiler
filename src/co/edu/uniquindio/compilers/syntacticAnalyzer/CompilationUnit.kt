package co.edu.uniquindio.compilers.syntacticAnalyzer

import javafx.scene.control.TreeItem

class CompilationUnit(var functionsList:ArrayList<Function>, var variableInitialization: ArrayList<VariableInitialization>) {
    override fun toString(): String {
        return "CompilationUnit(variableDeclarationList=$variableInitialization,functionsList=$functionsList )"
    }

    fun getTreeView():TreeItem<String>{

        var root:TreeItem<String> = TreeItem("Unidad de Compilación")

        for(variableDeclaration in variableInitialization){
            root.children.add(variableDeclaration.getTreeView())
        }
        for(function in functionsList){
            root.children.add(function.getTreeView())
        }
        return root
    }
}