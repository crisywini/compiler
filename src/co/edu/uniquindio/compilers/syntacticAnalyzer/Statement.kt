package co.edu.uniquindio.compilers.syntacticAnalyzer
import javafx.scene.control.TreeItem

open class Statement {

    open fun getTreeView():TreeItem<String>{
        return TreeItem("")
    }
}