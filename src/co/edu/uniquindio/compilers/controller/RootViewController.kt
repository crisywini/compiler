package co.edu.uniquindio.compilers.controller

import co.edu.uniquindio.compilers.app.App
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
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
    lateinit var intView:AnchorPane
    lateinit var initViewController: InitViewController

    /**
     * Show Init View method
     */
    fun showInitView(){
        val loader = FXMLLoader(App::class.java.getResource("/InitView.fxml"))
        val initView: Parent = loader.load()
        val controller:InitViewController = loader.getController()
        controller.initizalize()
        rootPane.center=initView
    }
}