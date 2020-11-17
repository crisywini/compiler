package co.edu.uniquindio.compilers.syntacticAnalyzer

import javafx.scene.control.TreeItem

class CompilationUnit(var functionsList:ArrayList<Function>, var variableDeclarationList:ArrayList<VariableDeclaration>) {
    override fun toString(): String {
        return "CompilationUnit(variableDeclarationList=$variableDeclarationList,functionsList=$functionsList )"
    }

    fun getTreeView():TreeItem<String>{

        var root:TreeItem<String> = TreeItem("Unidad de Compilación")

        for(variableDeclaration in variableDeclarationList){
            root.children.add(variableDeclaration.getTreeView())
        }
        for(function in functionsList){
            root.children.add(function.getTreeView())
        }
        return root
    }
}