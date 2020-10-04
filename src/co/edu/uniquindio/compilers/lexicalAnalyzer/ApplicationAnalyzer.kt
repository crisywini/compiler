package co.edu.uniquindio.compilers.test

fun main(){
    val lexico = LexicalAnalyzer("           4654.64    asd asd321 654\nasdasdasd789654")
    lexico.analyze()
    print(lexico.tokenList)
}