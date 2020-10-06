package co.edu.uniquindio.compilers.controller

import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.observables.TokenObservable
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea

import javafx.scene.control.cell.PropertyValueFactory


class InitViewController {

    //Relajese que nosotros vamos a inicializar esa variable en algún momento
    @FXML lateinit var sourceCodeTextArea: TextArea
    @FXML lateinit var tokensTableView: TableView<TokenObservable>
    @FXML lateinit var lexemaTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var categoryTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var rowTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var columnTableColumn: TableColumn<TokenObservable, String>


    @FXML
    fun analyze(event : ActionEvent){
        if(sourceCodeTextArea.length>0){
            fillTableView()
        }
    }
    private fun fillTableView(){
        val lexical = LexicalAnalyzer(sourceCodeTextArea.text)
        lexical.analyze()
        tokensTableView.items.clear()
        for(element in lexical.tokenList){
            tokensTableView.items.add(TokenObservable(element.lexema,element.category.toString()
                    ,"".plus(element.row),"".plus(element.column)))
        }
        tokensTableView.refresh()
    }


    fun initizalize(){

        initTableView()
    }

    private fun initTableView(){
        lexemaTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("lexema")
        categoryTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("category")
        rowTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("row")
        columnTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("column")
    }
}