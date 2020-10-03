package co.edu.uniquindio.compilers.test

fun main(){
    val lexico = AnalizadorLexico("           4654.64    asd asd321 654\nasdasdasd789654")
    lexico.analizar()
    print(lexico.listaTokens)
}