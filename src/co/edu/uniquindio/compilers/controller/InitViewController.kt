package co.edu.uniquindio.compilers.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextField

class InitViewController {

    //Relajese que nosotros vamos a inicializar esa variable en algún momento
    @FXML
    lateinit var sourceCodeTextField: TextField
    @FXML
    lateinit var tokensTableView: TableView

    @FXML
    fun changeValues(event : ActionEvent){
        val aux = textField1.text
        textField1.text = textField2.text
        textField2.text = aux

    }
}