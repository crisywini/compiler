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

    fun getJavaCode ():String{
        if(category== Category.PALABRA_RESERVADA){
            if(lexema=="bemol"){
                return "double"
            }else if(lexema=="becu"){
                return "int"
            }else if(lexema=="pulso"){
                return "boolean"
            }else if(lexema=="bridge"){
                return "String"
            }else if(lexema=="ante"){
                return "char"
            }else if(lexema=="coda"){
                return "return"
            }else if(lexema=="rondo"){
                return "while"
            }else if(lexema=="GP"){
                return "break"
            }else if(lexema=="largo"){
                return "long"
            }else if(lexema=="tutti"){
                return "public"
            }else if(lexema=="solo"){
                return "private"
            }else if(lexema=="proteto"){
                return "protected"
            }else if(lexema=="eva"){
                return "if"
            }else if(lexema=="contra"){
                return "else"
            }else if(lexema=="option"){
                return "switch"
            }else if(lexema=="rast"){
                return "case"
            }else if(lexema=="cedez"){
                return "static"
            }

        }else if(category==Category.OPERADORES_RELACIONALES){
            if(lexema=="<-"){
                return "<"
            }
        }
        return lexema
    }
}