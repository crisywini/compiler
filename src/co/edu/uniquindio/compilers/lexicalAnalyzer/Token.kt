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
            } else if(lexema=="dacapo"){
                return "void"
            }

        }else if(category==Category.OPERADOR_ARITMETICO){
            if(lexema=="*"){
                return "+"
            }else if(lexema=="'"){
                return "-"
            }else if(lexema=="."){
                return "*"
            }else if(lexema=="-"){
                return "/"
            }else if(lexema=="&"){
                return "%"
            }
        }else if(category==Category.OPERADORES_RELACIONALES){
            if(lexema=="<-"){
                return "<"
            }else if(lexema=="->"){
                return ">"
            }else if(lexema==">:"){
                return ">="
            }else if(lexema=="<:"){
                return "<="
            }else if(lexema=="=="){
                return "::"
            }else if(lexema=="!:"){
                return "!="
            }
        }else if(category==Category.OPERADOR_LOGICO){
            if(lexema=="or"){
                return "||"
            }else if(lexema=="and"){
                return "&&"
            }
        }else if(category==Category.OPERADOR_ASIGNACION){
            if(lexema=="*:"){
                return "+="
            }else if(lexema=="':"){
                return "-="
            }else if(lexema==".:"){
                return "*="
            }else if(lexema=="-:"){
                return "/="
            }else if(lexema=="&:"){
                return "%="
            }else if(lexema==":"){
                return "="
            }
        }else if(category==Category.LLAVE_IZQUIERDA){
            if(lexema=="<"){
                return "{"
            }
        }else if(category==Category.LLAVE_DERECHA){
            if(lexema==">"){
                return "}"
            }
        }else if(category==Category.PARENTESIS_IZQUIERDO){
            if(lexema=="["){
                return "("
            }
        }else if(category==Category.PARENTESIS_DERECHO){
            if(lexema=="]"){
                return ")"
            }
        }else if(category==Category.CORCHETE_IZQUIERDO){
            if(lexema=="{"){
                return "["
            }
        }else if(category==Category.CORCHETE_DERECHO){
            if(lexema=="}"){
                return "]"
            }
        }else if(category==Category.OPERADOR_INCREMENTO){
            if(lexema=="**"){
                return "++"
            }
        }else if(category==Category.OPERADOR_DECREMENTO){
            if(lexema=="''"){
                return "--"
            }
        }else if(category==Category.TERMINAL){
            if(lexema=="\\"){
                return ";"
            }
        } else if(category==Category.PUNTO){
            if(lexema==","){
                return "."
            }
        }else if(category==Category.DOS_PUNTOS){
            if(lexema==";"){
                return ":"
            }
        }else if(category==Category.SEPARADOR){
            if(lexema=="_"){
                return ","
            }
        } else if(category==Category.COMENTARIO){
            if(lexema=="[["){
                return "//"
            }
            else{
                return lexema.replace("^","/**").replace("^","*/")
            }
        } else if(category==Category.IDENTIFICADOR || category==Category.ENTERO ||category==Category.DECIMAL){
                return lexema.substring(1)
        } else if(category==Category.CADENA_CARACTERES){
            return lexema.replace("(","\"").replace(")","\"")
        }else if(category==Category.CARACTER){
            return lexema.replace("¿","\'").replace("?","\'")
        }

        return lexema
    }
}