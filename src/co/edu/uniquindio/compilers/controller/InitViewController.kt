package co.edu.uniquindio.compilers.controller

import co.edu.uniquindio.compilers.app.App
import co.edu.uniquindio.compilers.lexicalAnalyzer.LexicalAnalyzer
import co.edu.uniquindio.compilers.observables.ErrorObservable
import co.edu.uniquindio.compilers.observables.TokenObservable
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea

import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 *
 * Init view controller Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class InitViewController {

    @FXML lateinit var sourceCodeTextArea: TextArea
    @FXML lateinit var tokensTableView: TableView<TokenObservable>
    @FXML lateinit var lexemaTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var categoryTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var rowTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var columnTableColumn: TableColumn<TokenObservable, String>
    @FXML lateinit var imageButton:ImageView
    @FXML lateinit var errorsTableView: TableView<ErrorObservable>
    @FXML lateinit var errorErrorTableColumn: TableColumn<ErrorObservable, String>
    @FXML lateinit var categoryErrorTableColumn: TableColumn<ErrorObservable, String>
    @FXML lateinit var rowErrorTableColumn: TableColumn<ErrorObservable, String>
    @FXML lateinit var columnErrorTableColumn: TableColumn<ErrorObservable, String>

    @FXML
    fun analyze(event : ActionEvent){
        if(sourceCodeTextArea.length>0){
            val lexical = LexicalAnalyzer(sourceCodeTextArea.text)
            lexical.analyze()
            fillTokensTableView(lexical)
            fillErrorsTableView(lexical)
        }
    }

    /**
     * fill Tokens Table View method
     */
    private fun fillTokensTableView(lexical:LexicalAnalyzer){
        tokensTableView.items.clear()
        for(element in lexical.tokenList){
            tokensTableView.items.add(TokenObservable(element.lexema,element.category.toString()
                    ,"".plus(element.row),"".plus(element.column)))
        }
        tokensTableView.refresh()
    }

    /**
     * Fill Errors Table View method
     */
    private fun fillErrorsTableView(lexical:LexicalAnalyzer){
        errorsTableView.items.clear()
        for(element in lexical.errorList){
            errorsTableView.items.add(ErrorObservable(element.error, "".plus(element.row), "".plus(element.column)
                    , element.errorCategory.toString()))
        }
        errorsTableView.refresh()
    }

    /**
     * Initizalize method
     */
    fun initizalize(){
        initTokensTableView()
        initErrorsTableView()
        imageButton.image = Image(App::class.java.getResourceAsStream("/analyzer.png"))
    }

    /**
     * Init Tokens Table View method
     */
    private fun initTokensTableView(){
        lexemaTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("lexema")
        categoryTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("category")
        rowTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("row")
        columnTableColumn.cellValueFactory = PropertyValueFactory<TokenObservable, String>("column")
    }

    /**
     * Init Errors Table View method
     */
    private fun initErrorsTableView(){
        errorErrorTableColumn.cellValueFactory = PropertyValueFactory<ErrorObservable, String>("error")
        categoryErrorTableColumn.cellValueFactory = PropertyValueFactory<ErrorObservable, String>("errorCategory")
        rowErrorTableColumn.cellValueFactory = PropertyValueFactory<ErrorObservable, String>("row")
        columnErrorTableColumn.cellValueFactory = PropertyValueFactory<ErrorObservable, String>("column")
    }
}