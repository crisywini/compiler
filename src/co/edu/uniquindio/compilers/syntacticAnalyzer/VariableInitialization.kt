package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token
import javafx.scene.control.TreeItem

class VariableInitialization(var dataType:Token?, var identifier:Token?, var value:Token?):Statement() {

    override fun getTreeView(): TreeItem<String> {
        val root:TreeItem<String> = TreeItem("Variable Inmutable")
        root.children.add(TreeItem("Tipo de dato: ${dataType?.lexema}"))
        root.children.add(TreeItem("Identificador: ${identifier?.lexema}"))
        root.children.add(TreeItem("Valor: ${value?.lexema}"))
        return root
    }

    override fun toString(): String {
        return "VariableInitialization(dataType=$dataType, identifier=$identifier, value=$value)"
    }
    override fun getJavaCode(): String {
        var code=identifier!!.getJavaCode()+"="
        code+=value!!.getJavaCode()+";"
        return code
    }
}