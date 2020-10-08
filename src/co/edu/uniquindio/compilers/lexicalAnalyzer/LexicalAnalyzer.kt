package co.edu.uniquindio.compilers.lexicalAnalyzer

class LexicalAnalyzer (var sourceCode:String  ){
    var actualPosition = 0
    var currentCharacter = sourceCode[0] //First token of the source code
    var tokenList = ArrayList<Token>()
    var endCode = 0.toChar()
    var currentRow = 0
    var currentColumn = 0

    /**
     * This method allows to analyze the source code and identify the tokens
     */
    fun analyze(){
        while(currentCharacter!=endCode){

            if(currentCharacter == ' '|| currentCharacter=='\t'||currentCharacter=='\n'){
                setNextCharacter()
                continue
            }
            //if(isInteger()) continue
            //if(isDecimal()) continue
            if(isString()) continue
            if(isIdentifier()) continue
            if(isArithmeticOperator()) continue
            if(isIncrementOperator()) continue
            if(isDecrementOperator()) continue
            if(isAssignmentOperator()) continue
            if(isRelationalOperator()) continue
            storeToken(""+currentCharacter, Category.DESCONOCIDO, currentRow, currentColumn)
            setNextCharacter()
        }
    }

    fun isDecimal():Boolean{
        if(currentCharacter=='.'||currentCharacter.isDigit()){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            if(currentCharacter=='.'){
                lexema += currentCharacter
                setNextCharacter()
                if(currentCharacter.isDigit()){
                    lexema+=currentCharacter
                    setNextCharacter()
                }
            }else{
                lexema += currentCharacter
                setNextCharacter()
                while(currentCharacter.isDigit()){
                    lexema+=currentCharacter
                    setNextCharacter()
                }
                if(currentCharacter=='.'){

                    lexema+=currentCharacter
                    setNextCharacter()


                }
            }
            while(currentCharacter.isDigit()){
                lexema+=currentCharacter
                setNextCharacter()
            }
            storeToken(lexema, Category.DECIMAL, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to do backtracking to reset the values
     */
    fun doBackTracking(initialPosition:Int, initialRow:Int, initialColumn:Int){
        actualPosition = initialPosition
        currentRow = initialRow
        currentColumn = initialColumn

        currentCharacter = sourceCode[actualPosition]
    }

    fun isString():Boolean{

        if(currentCharacter=='('){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while(currentCharacter!=')'){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }
            lexema += currentCharacter
            setNextCharacter()
            storeToken(lexema, Category.CADENA_CARACTERES, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the arithmetic operator
     */
    fun isArithmeticOperator():Boolean{
        if(currentCharacter=='*'||currentCharacter=='\''||currentCharacter=='.'||currentCharacter=='-'||currentCharacter=='&'){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if(currentCharacterCopy=='*'&&currentCharacter=='*'||currentCharacterCopy=='\''&&currentCharacter=='\''||currentCharacter==':'
                    ||(currentCharacterCopy=='-'&&currentCharacter=='>')){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_ARITMETICO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the increment operator
     */
    fun isIncrementOperator():Boolean{
        if(currentCharacter=='*'){

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if(currentCharacter=='*'){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter

            }else if(currentCharacter==':'){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_INCREMENTO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the decrement operator
     */
    fun isDecrementOperator():Boolean{
        if(currentCharacter=='\''){

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if(currentCharacter=='\''){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }else if(currentCharacter==':'){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_DECREMENTO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the assignment operators
     */
    fun isAssignmentOperator():Boolean {
        if (currentCharacter == '*'
                || currentCharacter == '\''
                || currentCharacter == '.'
                || currentCharacter == '-'
                || currentCharacter == '&'
                ||currentCharacter==':'){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter

            if(currentCharacter==':'&&(currentCharacterCopy == '*'
                            || currentCharacterCopy == '\''
                            || currentCharacterCopy == '.'
                            || currentCharacterCopy == '-'
                            || currentCharacterCopy == '&')){
                lexema += currentCharacter
                setNextCharacter()
            }else if ((currentCharacterCopy == ':' && currentCharacter == ':') ||
                        (currentCharacterCopy == '*' && currentCharacter == '*') ||
                        (currentCharacterCopy == '\'' && currentCharacter == '\'')||
                        (currentCharacterCopy=='-'&&currentCharacter=='>')) {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
            }
            storeToken(lexema, Category.OPERADOR_ASIGNACION, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the relational operators
     */
    fun isRelationalOperator():Boolean{
        if(currentCharacter=='>'||currentCharacter==':'||currentCharacter=='<'
                ||currentCharacter=='-'||currentCharacter=='!'){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if((currentCharacterCopy=='>'&&currentCharacter==':')||
                    (currentCharacterCopy=='<'&&(currentCharacter=='-'||currentCharacter==':'))||
                    (currentCharacterCopy=='-' &&currentCharacter=='>')||
                    (currentCharacterCopy=='!'&&currentCharacter==':')||
                    (currentCharacterCopy==':'&&currentCharacter==':')) {
                lexema += currentCharacter
                setNextCharacter()
            }else if((currentCharacterCopy=='!'&&currentCharacter!=':')||
                    (currentCharacterCopy=='-'&&currentCharacter==':')||
                    currentCharacterCopy=='>'||currentCharacterCopy=='<'){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }

            storeToken(lexema,Category.OPERADORES_RELACIONALES, initialRow,initialColumn)
            return true
        }
        return false
    }



    fun isInteger():Boolean{
        if(currentCharacter.isDigit()){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition

            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while(currentCharacter.isDigit()){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }

            if(currentCharacter=='.'){
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }


            storeToken(lexema, Category.ENTERO, initialRow, initialColumn)
            return true
        }
        //RI -> Rechazo Inmediato
        return false
    }


    fun isIdentifier():Boolean{
        if(currentCharacter.isLetter()||currentCharacter=='$'||currentCharacter=='_'){
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn


            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while(currentCharacter.isLetter()||currentCharacter=='$'||currentCharacter=='_'||currentCharacter.isDigit()){
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }
            storeToken(lexema, Category.IDENTIFICADOR, initialRow, initialColumn)
            return true
        }
        return false
    }


    /**
     * This method allows to:
     * increment the actual position,
     * update the current character,
     * verify the correct index of the source code
     */
    fun setNextCharacter(){
        if(actualPosition==sourceCode.length-1){
            currentCharacter = endCode
        }else{

            if(currentCharacter == '\n'){
                currentRow++
                currentColumn=0
            }else{
                currentColumn++
            }

            actualPosition++
            currentCharacter = sourceCode[actualPosition]
        }
    }

    /**
     * This method allows to fill the token list
     */
    fun storeToken(lexema:String, category:Category, row:Int, column:Int) = tokenList.add(Token(lexema, category, row, column))
}