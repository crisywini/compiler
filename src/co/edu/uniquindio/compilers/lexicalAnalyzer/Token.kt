package co.edu.uniquindio.compilers.lexicalAnalyzer

/**
 *
 * Token Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class Token (var lexema:String, var category:Category, var row:Int, var column:Int){
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$category, fila=$row, columna=$column)"
    }
}