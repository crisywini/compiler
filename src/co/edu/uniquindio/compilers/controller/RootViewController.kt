package co.edu.uniquindio.compilers.controller

import co.edu.uniquindio.compilers.app.App
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane

class RootViewController {
    @FXML lateinit var rootPane:BorderPane
    lateinit var intView:AnchorPane
    lateinit var initViewController: InitViewController

    fun showInitView(){
        val loader = FXMLLoader(App::class.java.getResource("/InitView.fxml"))
        val initView: Parent = loader.load()
        val controller:InitViewController = loader.getController()
        controller.initizalize()
        rootPane.center=initView
    }
}