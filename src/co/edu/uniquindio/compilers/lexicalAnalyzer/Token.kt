package co.edu.uniquindio.compilers.lexicalAnalyzer

class Token (var lexema:String, var category:Category, var row:Int, var column:Int){
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$category, fila=$row, columna=$column)"
    }
}