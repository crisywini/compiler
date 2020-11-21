package co.edu.uniquindio.compilers.app

import co.edu.uniquindio.compilers.controller.RootViewController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 *
 * Main Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class App : Application(){
    /**
     * start method
     */
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(App::class.java.getResource("/RootView.fxml"))
        val parent:Parent = loader.load()
        val controller:RootViewController= loader.getController()
        val scene = Scene(parent)
        //Operador para variables que pueden ser nulas
        primaryStage?.scene = scene
        primaryStage?.title = "Compiler"
        primaryStage?.show()
    }

    /**
     * main method
     */
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(App::class.java)
        }
    }
}