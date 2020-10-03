package co.edu.uniquindio.compilers.test

class AnalizadorLexico (var codigoFuente:String  ){
    var posicionActual = 0
    var caracterActual = codigoFuente[0] //Primer caracter del código fuente
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    fun analizar(){
        while(caracterActual!=finCodigo){

            if(caracterActual == ' '|| caracterActual=='\t'||caracterActual=='\n'){
                obtenerSiguienteCaracter()
                continue
            }
            if(esEntero()) continue
            if(esDecimal()) continue

            if(esIdentificador()) continue
            almacenarToken(""+caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esDecimal():Boolean{
        if(caracterActual=='.'||caracterActual.isDigit()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            if(caracterActual=='.'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual.isDigit()){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }
            }else{
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while(caracterActual.isDigit()){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }
                if(caracterActual=='.'){

                    lexema+=caracterActual
                    obtenerSiguienteCaracter()


                }
            }
            while(caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
            return true
        }
        return false
    }
    fun hacerBackTracking(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial

        caracterActual = codigoFuente[posicionActual]
    }


    fun esEntero():Boolean{
        if(caracterActual.isDigit()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()//Cambiar nombre a actualizar caracter
            while(caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()//Cambiar nombre a actualizar caracter
            }

            if(caracterActual=='.'){
                hacerBackTracking(posicionInicial, filaInicial, columnaInicial)
                return false
            }


            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        //RI -> Rechazo Inmediato
        return false
    }

    fun esIdentificador():Boolean{
        if(caracterActual.isLetter()||caracterActual=='$'||caracterActual=='_'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual


            lexema += caracterActual
            obtenerSiguienteCaracter()//Cambiar nombre a actualizar caracter
            while(caracterActual.isLetter()||caracterActual=='$'||caracterActual=='_'||caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()//Cambiar nombre a actualizar caracter
            }
            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Único método que debe hacer:
     * aumentar posición,
     * actualizar caracter actual
     * verificar que no se desborde el codigo fuente
     *
     */
    fun obtenerSiguienteCaracter(){
        if(posicionActual==codigoFuente.length-1){
            caracterActual = finCodigo
        }else{

            if(caracterActual == '\n'){
                filaActual++
                columnaActual=0
            }else{
                columnaActual++
            }

            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

    /**
     * Almacena tokens en la lista de tokens
     */
    fun almacenarToken(lexema:String, categoria:Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema, categoria, fila, columna))
}