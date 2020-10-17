package co.edu.uniquindio.compilers.lexicalAnalyzer

/**
 *
 * Error Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class Error(var error:String, var row:Int, var column:Int, var errorCategory: ErrorCategory) {
    override fun toString(): String {
        return "Error(error='$error', row=$row, column=$column, errorCategory=$errorCategory)"
    }
}