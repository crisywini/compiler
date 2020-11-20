package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class Switch (var name:Token, var caseList:ArrayList<Case>):Statement(){

    override fun toString(): String {
        return "Argument(name=$name, caseList=$caseList)"
    }
    override fun getTreeView(): TreeItem<String> {
        var treeName = TreeItem("${name.lexema}")
        var treeCase = TreeItem("Casos")

        for (case in caseList){
            treeCase.children.add(case.getTreeView())
        }
        var tree = TreeItem("switch")
        tree.children.add(treeName)
        tree.children.add(treeCase)

        return tree
    }
}