package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.semanticAnalyzer.SymbolsTable
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

    fun fillTableSymbols(symbolsTable:SymbolsTable, semanticErrorsList:ArrayList<Error>){
        for(function in functionsList){
            function.fillTableSymbols(symbolsTable, semanticErrorsList, "unidadCompilacion")
        }
    }

    fun analyzeSemantic(symbolsTable:SymbolsTable, semanticErrorsList:ArrayList<Error>){
        for(function in functionsList){
            function.analyzeSemantic(symbolsTable, semanticErrorsList)
        }
    }

    fun getJavaCode (): String{
        var code= "import javax.swing.JOptionPane; public class Principal {"

        for(variable in variableInitialization){
            code+= variable.getJavaCode()
        }
        for( funtion in functionsList){
            code+= funtion.getJavaCode()
        }

        code+= "}"
        return code
    }
}