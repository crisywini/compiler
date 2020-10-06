package co.edu.uniquindio.compilers.app

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class App : Application(){

    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(App::class.java.getResource("/InitView.fxml"))
        val parent:Parent = loader.load()

        val scene = Scene(parent)
        //Operador para variables que pueden ser nulas
        primaryStage?.scene = scene
        primaryStage?.title = "Compiler"
        primaryStage?.show()
    }
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(App::class.java)
        }


    }

}