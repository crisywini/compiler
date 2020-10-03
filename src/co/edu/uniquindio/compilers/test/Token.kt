package co.edu.uniquindio.compilers.test

class Token (var lexema:String, var categoria:Categoria, var fila:Int, var columna:Int){
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
}