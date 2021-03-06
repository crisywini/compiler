package co.edu.uniquindio.compilers.controller

import co.edu.uniquindio.compilers.app.App
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane

/**
 *
 * Root View Controller Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class RootViewController {
    @FXML lateinit var rootPane:BorderPane
    @FXML lateinit var initViewController: InitViewController
    companion object{
        @JvmStatic
        fun showAlert(contentText:String, title:String, type:Alert.AlertType){
            var alert = Alert(type)
            alert.contentText = contentText
            alert.title = title
            alert.headerText = ""
            alert.showAndWait()
        }
    }
}