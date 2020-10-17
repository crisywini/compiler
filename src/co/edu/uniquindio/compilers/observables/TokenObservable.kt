package co.edu.uniquindio.compilers.observables
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

/**
 *
 * Token Observable Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class TokenObservable(var lexema:String, var category: String, var row:String, var column:String) {

    override fun toString(): String {
        return "TokenObservable(lexema=${lexema}, category=${category}, row=${row}, column=${column})"
    }
}