package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Category
import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class SyntacticAnalyzer(var tokenList: ArrayList<Token>) {
    var currentPosition = 0
    var currentToken: Token = tokenList[0]
    var errorList = ArrayList<Error>()

    /**
     * This method allows to set the current token to the next token
     */
    private fun setNextToken() {
        currentPosition++
        if (currentPosition < tokenList.size) {
            currentToken = tokenList[currentPosition]
        }
    }

    /**
     * This method allows to do backtracking at one initial position
     */
    private fun doBacktracking(initialPosition: Int) {
        currentPosition = initialPosition
        currentToken = tokenList[currentPosition]
    }

    /**
     * This method allows to save an error in the error list
     */
    private fun reportError(message: String) {
        errorList.add(Error(message, currentToken.row, currentToken.column, ErrorCategory.ERROR_SINTACTICO))
    }

    /**
     * <CompilationUnit> ::= [<VariableDeclarationList> ] <FunctionsList>
     */
    fun isCompilationUnit(): CompilationUnit? {
        val immutableVariableInitialization: ArrayList<VariableInitialization> = isImmutableVariableInitializationList()
        val functionsList: ArrayList<Function> = isFunctionList()
        return if (functionsList.size > 0) {
            CompilationUnit(functionsList, immutableVariableInitialization)
        } else null
    }


    /**
     * <VariableDeclarationList> ::= <VariableDeclaration>[<VariableDeclarationList>]
     */
    fun isVariableDeclarationList(): ArrayList<VariableDeclaration> {
        var variableDeclarationList = ArrayList<VariableDeclaration>()
        var variableDeclaration = isVariableDeclaration()
        while (variableDeclaration != null) {
            variableDeclarationList.add(variableDeclaration)
            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti") {
                break
            }
            variableDeclaration = isVariableDeclaration()
        }
        return variableDeclarationList
    }

    /**
     * <VariableDeclaration> ::= <MutableVariableDeclaration>|<ImmutableVariableDeclaration>
     */
    fun isVariableDeclaration(): VariableDeclaration? {

        var variableDeclaration = isMutableVariableDeclaration()
        return if (variableDeclaration != null) {
            variableDeclaration
        } else {
            variableDeclaration = isImmutableVariableDeclaration()
            variableDeclaration
        }
    }

    /**
     * <MutableVariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
     */
    fun isMutableVariableDeclaration(): VariableDeclaration? {
        val dataType = isDataType()
        if (dataType != null) {
            setNextToken()
            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti") {
                setNextToken()
                if (currentToken.category == Category.DOS_PUNTOS) {
                    setNextToken()
                    val identifierList = isIdentifierList()
                    if (identifierList.size > 0) {
                        if (currentToken.category == Category.TERMINAL) {
                            setNextToken()
                            return VariableDeclaration(dataType, identifierList)
                        } else {
                            reportError("Falta el terminal \\")
                        }
                    } else {
                        reportError("Faltan los identificadores")
                    }
                } else {
                    reportError("Faltan ;")
                }
            } else {
                reportError("Falta la palabra reservada")
            }
        }
        return null
    }

    /**
     * <VariableInitializationList> ::= <ImmutableVariableInitialization>[<VariableInitializationList>]
     */
    fun isImmutableVariableInitializationList(): ArrayList<VariableInitialization> {
        val variableList: ArrayList<VariableInitialization> = ArrayList()
        var variableInitialization = isImmutableVariableInitialization()
        while (variableInitialization != null) {
            variableList.add(variableInitialization)
            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti") {
                break
            }
            variableInitialization = isImmutableVariableInitialization()
        }
        return variableList
    }

    /**
     * <ImmutableVariableInitialization> ::= <DataType> solo “;” identifier “:” <Value> “\”
     *
     */
    fun isImmutableVariableInitialization(): VariableInitialization? {
        val dataType = isDataType()
        if (dataType != null) {
            setNextToken()
            val initialPosition1 = currentPosition
            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "solo") {
                setNextToken()
                if (currentToken.category == Category.DOS_PUNTOS) {
                    setNextToken()
                    if (currentToken.category == Category.IDENTIFICADOR) {
                        val identifier = currentToken
                        setNextToken()
                        val initialPosition2 = currentPosition
                        if (currentToken.category == Category.OPERADOR_ASIGNACION) {
                            setNextToken()
                            val value = isValue()
                            if (value != null) {
                                setNextToken()
                                if (currentToken.category == Category.TERMINAL) {
                                    setNextToken()
                                    return VariableInitialization(dataType, identifier,  value)
                                }else{
                                    reportError("Falta la terminal en la variable inmutable")
                                }
                            }else{
                                reportError("Falta el valor de la variable en la variable inmutable")
                            }
                        } else {
                            doBacktracking(initialPosition2)
                        }
                    }else{
                        reportError("Falta el identificador en la variable inmutable")
                    }
                }else{
                    reportError("Falta el ; en la variable inmutable")
                }
            } else {
                doBacktracking(initialPosition1)
            }
        }
        return null
    }

    /**
     * <Value> ::= String | Integer | Decimal | Boolean
     */
    fun isValue():Token?{
        if(currentToken.category == Category.CADENA_CARACTERES || currentToken.category == Category.DECIMAL || currentToken.category == Category.ENTERO
                || (currentToken.category == Category.PALABRA_RESERVADA &&(currentToken.lexema == "kronos" || currentToken.lexema ==  "zeus"))
                ||currentToken.category == Category.CARACTER){
            return currentToken
        }
        return null
    }

    /**
     *  <ImmutableVariableDeclaration> ::= <DataType> solo ";" <IdentifiersList> " \ "
     */
    fun isImmutableVariableDeclaration(): VariableDeclaration? {
        val dataType = isDataType()
        if (dataType != null) {
            setNextToken()
            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "solo") {
                setNextToken()
                if (currentToken.category == Category.DOS_PUNTOS) {
                    setNextToken()
                    val identifierList = isIdentifierList()
                    if (identifierList.size > 0) {
                        if (currentToken.category == Category.TERMINAL) {
                            setNextToken()
                            return VariableDeclaration(dataType, identifierList)
                        } else {
                            reportError("Falta el terminal \\")
                        }
                    } else {
                        reportError("Faltan los identificadores")
                    }
                } else {
                    reportError("Faltan ;")
                }
            } else {
                reportError("Falta la palabra reservada")
            }
        }
        return null
    }

    /**
     * <IdentifierList> ::= identifier ["_"<IdentifierList>]
     */
    fun isIdentifierList(): ArrayList<Token> {
        val identifierList = ArrayList<Token>()
        var identifier: Token?
        if (currentToken.category == Category.IDENTIFICADOR) {
            identifier = currentToken
            setNextToken()
        } else {
            identifier = null
        }
        while (identifier != null) {
            identifierList.add(identifier)
            if (currentToken.category == Category.SEPARADOR) {
                setNextToken()
                identifier = if (currentToken.category == Category.IDENTIFICADOR) {
                    currentToken
                } else {
                    null
                }
            } else if (currentToken.category == Category.TERMINAL) {
                break
            } else {
                reportError("Falta separador")
                break
            }
            setNextToken()
        }
        return identifierList
    }

    /**
     * <FunctionsList> ::= <Function>[<FunctionsList>]
     */
    fun isFunctionList(): ArrayList<Function> {
        var functionList = ArrayList<Function>()
        var function = isFunction()

        while (function != null) {
            functionList.add(function)
            if (currentPosition == tokenList.size) {
                break
            }
            function = isFunction()
        }
        return functionList
    }

    /**else{
    reportError("Falta el tipo de dato")
    }
     * <Function> ::= tutti identifier "["[<ParamList>]"]" <StatementBlock> <ReturnType>
     *
     */
    fun isFunction(): Function? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti") {
            setNextToken()
            if(currentToken.category == Category.IDENTIFICADOR ){
                val identifier = currentToken
                setNextToken()
                if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                    setNextToken()
                    val paramList = isParamList()
                    if(currentToken.category == Category.PARENTESIS_DERECHO){
                        setNextToken()
                        print(currentToken.lexema)
                        print("FUNCIÓN TOKEN \n")
                        val statementBlock = isStatementBlock()
                        print(statementBlock)
                        print(" FUNCIÓN \n")
                        if(statementBlock != null){
                            val returnType = isReturnType()
                            if(returnType!=null){
                                setNextToken()
                                //This function is correct
                                return Function(identifier,returnType, paramList, statementBlock)
                            }else{
                                reportError("El tipo de retorno está vacio en la función")
                            }
                        }else{
                            reportError("Falta el bloque de sentencias en la función")
                        }
                    }else{
                        reportError("Falta el paréntesis derecho en la función")
                    }
                }else{
                    reportError("Falta el paréntesis izquierdo en la función")
                }
            }else{
                reportError("Falta el identificador en la función")
            }
        }
        return null
    }

    /**
     * <Decision> ::= eva “[“ <LogicalExpression> “]” <StatementBlock>  [contra  <StatementBlock>]
     */

    fun isDecision(): Decision? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "eva") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                var expression = isLogicalExpression()
                if (expression != null) {
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        var statementBlock = isStatementBlock()
                        if (statementBlock != null) {
                            if (currentToken.lexema == "contra") {
                                setNextToken()
                                var statementBlock2 = isStatementBlock()
                                if (statementBlock2 != null) {
                                    return Decision(expression, statementBlock, statementBlock2)
                                }
                            } else {
                                return Decision(expression, statementBlock, null)
                            }
                        }else{
                            reportError("Falta bloque de sentencias en el eva")
                        }
                    }else{
                        reportError("Falta parentesis derecho en el eva")
                    }
                }else{
                    reportError("Falta la expresion logica en el eva")
                }
            }else{
                reportError("Falta parentesis izquierdo en el eva")
            }
        }
        return null;
    }

    /**
     * <CycleWhile>::= Rondo <logicalExpression> <StatementBlock>
     *
     */
    fun isCycle(): Cycle? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "rondo") {
            setNextToken()

            val expression = isLogicalExpression()
            if (expression != null) {
                val statementBlock = isStatementBlock()

                if(statementBlock != null) {
                    return Cycle(expression, statementBlock)
                }else{
                    reportError("Falta bloque de sentencias en el ciclo")
                }
            }else{
                reportError("Falta la expresion del ciclo")
            }
        }
        return null
    }

    /**
     * <Read>::= Proteto “[“ identifier “]” “\\”
     *
     */
    fun isRead(): Read? {

        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "proteto") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                if (currentToken.category == Category.IDENTIFICADOR) {
                    var identifier = currentToken
                    setNextToken()
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        if (currentToken.category == Category.TERMINAL) {
                            setNextToken()
                            return Read(identifier)
                        } else {
                            reportError("Falta el terminal")
                        }
                    } else {
                        reportError("Falta parentesis derecho")
                    }
                } else {
                    reportError("Falta el identificador")
                }
            } else {
                reportError("Falta parentesis izquierdo")
            }
        }
        return null
    }

    /**
     * <FunctionInvocation>::= identifier “[“ [<argumentList>] “]” “\”
     *
     */
    fun isFunctionInvocation(): FunctionInvocation? {

        if (currentToken.category == Category.IDENTIFICADOR) {
            var identifier = currentToken
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                var argumentList = isArgumentList()
                if (currentToken.category == Category.PARENTESIS_DERECHO) {
                    setNextToken()
                    if (currentToken.category == Category.TERMINAL) {
                        setNextToken()
                        return FunctionInvocation(identifier, argumentList)
                    } else {
                        reportError("Falta el terminal")
                    }
                } else {
                    reportError("Falta el parentesis derecho")
                }
            } else {
                reportError("Falta el parentesis izquierdo")
            }
        }
        return null
    }

    /**
     * <Increment>::= identifier IncrementOperator “\”
     *
     */
    fun isIncrement(): Increment? {

        if (currentToken.category == Category.IDENTIFICADOR) {
            var identifier = currentToken
            setNextToken()
            if (currentToken.category == Category.OPERADOR_INCREMENTO) {
                setNextToken()
                if (currentToken.category == Category.TERMINAL) {
                    setNextToken()
                    return Increment(identifier)
                } else {
                    reportError("Falta el terminal")
                }
            } else {
                reportError("Falta el operador de incremento")
            }
        }
        return null
    }

    /**
     * <Decrement>::= identifier DecrementOperator “\\”
     *
     */
    fun isDecrement(): Decrement? {

        if (currentToken.category == Category.IDENTIFICADOR) {
            var identifier = currentToken
            setNextToken()
            if (currentToken.category == Category.OPERADOR_DECREMENTO) {
                setNextToken()
                if (currentToken.category == Category.TERMINAL) {
                    setNextToken()
                    return Decrement(identifier)
                } else {
                    reportError("Falta el terminal")
                }
            } else {
                reportError("Falta el operador de decremento")
            }
        }
        return null
    }

    /**
     * <Return>::= coda identifier “\\” | coda <expressión> “\\”
     *
     */
    fun isReturn(): Return? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "coda") {
            setNextToken()

            if (currentToken.category == Category.IDENTIFICADOR) {
                var identifier = currentToken
                setNextToken()
                if (currentToken.category == Category.TERMINAL) {
                    setNextToken()
                    return Return(identifier, null)
                } else {
                    reportError("Falta el terminal")
                }
            } else {
                var expression = isExpression()
                if (expression != null) {
                    setNextToken()
                    if (currentToken.category == Category.TERMINAL) {
                        setNextToken()
                        return Return(null, expression)
                    } else {
                        reportError("Falta el terminal")
                    }
                } else {
                    reportError("Falta un identificador o una expresion")
                }
            }
        }
        return null
    }

    /**
     * <Print>::= opus “[“ identifier “]” “\\” | opus “[“<Expression>“]” “\\”
     *
     */
    fun isPrint(): Print? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "opus") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()

                if (currentToken.category == Category.IDENTIFICADOR) {
                    var identifier = currentToken
                    setNextToken()
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        if (currentToken.category == Category.TERMINAL) {
                            setNextToken()
                            return Print(identifier, null)
                        } else {
                            reportError("Falta el terminal")
                        }
                    } else {
                        reportError("Falta parentesis derecho")
                    }
                } else {
                    var expression = isExpression()
                    if (expression != null) {
                        setNextToken()
                        if (currentToken.category == Category.PARENTESIS_DERECHO) {
                            setNextToken()
                            if (currentToken.category == Category.TERMINAL) {
                                setNextToken()
                                return Print(null, expression)
                            } else {
                                reportError("Falta el terminal")
                            }
                        } else {
                            reportError("Falta parentesis derecho")
                        }
                    } else {
                        reportError("Falta un identificador o una expresion")
                    }
                }
            }
        }
        return null
    }

    /**
     * <parametro> ::= <TipoDato> identificador
     * <TipoDato> ::= becu | bemol | ante | bridge | pulso | largo
     */
    fun isParam(): Param? {

        val dataType = isDataType()

        if (dataType != null) {
            setNextToken()
            if (currentToken.category == Category.IDENTIFICADOR) {
                val name = currentToken
                setNextToken()

                return Param(name, dataType)
            } else {
                reportError("Falta el tipo de parámetro")
            }
        }
        return null
    }

    /**
     * <ParamList> ::= <Param>|["_"<ParamList>]
     */
    fun isParamList(): ArrayList<Param> {//Debe retornar una lista de parametros
        var paramList = ArrayList<Param>()
        var param = isParam()

        while (param != null) {
            paramList.add(param)
            if (currentToken.category == Category.SEPARADOR) {
                setNextToken()
            } else if (currentToken.category != Category.PARENTESIS_DERECHO) {
                reportError("Falta un separador en la lista de parámetros")
            }
            param = isParam()
        }
        return paramList
    }

    /**
     * <argument> ::= identificador | <Expression>
     */
    fun isArgument(): Argument? {
        val name = currentToken
        val expression = isExpression()
        if (name.category == Category.IDENTIFICADOR || expression != null) {
            return Argument(name, expression)
        }
        return null
    }

    /**
     * <argumentList> ::= <argument>|["_"<argumentList>]
     */
    fun isArgumentList(): ArrayList<Argument>? {
        var argumentList = ArrayList<Argument>()
        var argument = isArgument()

        while (argument != null) {
            argumentList.add(argument)
            if (currentToken.category == Category.SEPARADOR) {
                setNextToken()
            } else if (currentToken.category != Category.PARENTESIS_DERECHO) {
                reportError("Falta un separador en la lista de argumentos")
            } else {
                break
            }
            argument = isArgument()
        }
        return argumentList
    }

    /**
     * <StatementBlock> ::= "<"[<StatementList>]">"
     */
    fun isStatementBlock():ArrayList<Statement>?{//Debe retornar un bloque de sentencias

        if(currentToken.category == Category.LLAVE_IZQUIERDA){
            println(currentToken.lexema)

            setNextToken()
            val statementList = isStatementList()

            if(currentToken.category == Category.LLAVE_DERECHA){
                setNextToken()
                return statementList
            }
        }
        return null
    }

    /**
     * <StatementList> ::= <Statement> [<StatementList>]
     */
    fun isStatementList(): ArrayList<Statement> {
        val statementList = ArrayList<Statement>()
        var statement = isStatement()
        while (statement != null) {
            statementList.add(statement)
            statement = isStatement()
        }
        return statementList
    }

    /**
     * <Statement> ::= <Decision> | <VariableDeclaration> | <Assignment> | <Print> | <Cycle>
     *                  | <Return> | <Read> | <FunctionInvocation> | <Increment> | <Decrement>
     */
    fun isStatement():Statement?{

        var statement:Statement? = isVariableDeclaration()
        println("SENTENCIA DECLARACIÓN DE VARIABLE? ${statement != null}")
        if(statement != null){
            return statement
        }
        statement = isFunctionInvocation()
        println("SENTENCIA INVOCACIÓN DE FUNCIÓN? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isDecision()
        println("SENTENCIA DECISIÓN? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isAssignment()
        println("SENTENCIA ASIGNACIÓN? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isCycle()
        println("SENTENCIA CICLO? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isRead()
        println("SENTENCIA LECTURA? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isIncrement()
        println("SENTENCIA INCREMENTO? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isDecrement()
        println("SENTENCIA DECREMENTO? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isReturn()
        println("SENTENCIA RETORNO? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isPrint()
        println("SENTENCIA IMPRESIÓN? ${statement != null}")

        if(statement != null){
            return statement
        }
        statement = isSwitch()
        println("SENTENCIA SWITCH? ${statement != null}")

        if(statement != null){
            return statement
        }
        return null
    }

    /**
     * <Assignment>::= Identifier AssignmentOperator <Expression> “\”
     */
    fun isAssignment(): Assignment? {
        if (currentToken.category == Category.IDENTIFICADOR) {
            var identifier = currentToken
            setNextToken()
            if (currentToken.category == Category.OPERADOR_ASIGNACION) {
                setNextToken()
                var expression = isExpression()
                if (expression != null) {
                    setNextToken()
                    if (currentToken.category == Category.TERMINAL) {
                        setNextToken()
                        return Assignment(identifier, expression)
                    } else {
                        reportError("Falta el terminal")
                    }
                } else {
                    reportError("Falta la expresión")
                }
            } else {
                reportError("Falta el operador de asignación")
            }
        }
        return null
    }

    /**
     * <Expression> ::= <ArithmeticExpression> | <RelationalExpression> |
     *                      <LogicExpression> | <StringExpression>
     */
    fun isExpression():Expression?{
        var expression:Expression? = isArithmeticExpression()
        if(expression != null){
            return  expression
        }
        expression = isRelationalExpression()

        if (expression != null) {
            return expression
        }
        expression = isLogicalExpression()

        if (expression != null) {
            return expression
        }
        expression = isStringExpression()

        if (expression != null) {
            return expression
        }

        return null
    }

    /**
     * <ArithmeticExpression> ::= “[“ <ArithmeticExpression> “]” |
     *                            <ArithmeticExpression> ArithmeticOperator <ArithmeticExpression> |
     *                            <Number> |
     *                            identifier
     *
     * <ArithmeticExpression> ::= “[“ <ArithmeticExpression> “]”[ArithmeticOperator <ArithmeticExpression>] |
     *                          <Number>[ArithmeticOperator <ArithmeticExpression>]   |
     *                          identifier[ArithmeticOperator <ArithmeticExpression>]
     *                          TEST = GREAT
     */
    fun isArithmeticExpression(): ArithmeticExpression? {
        if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
            setNextToken()
            val expression1 = isArithmeticExpression()

            if (expression1 != null) {
                if (currentToken.category == Category.PARENTESIS_DERECHO) {
                    setNextToken()
                    if (currentToken.category == Category.OPERADOR_ARITMETICO) {
                        val operator = currentToken
                        setNextToken()
                        val expression2 = isArithmeticExpression()
                        if (expression2 != null) {
                            return ArithmeticExpression(expression1, operator, expression2)
                        } else {
                            reportError("Falta la expresión aritmética 2 en la expresión aritmética")
                        }
                    } else {
                        return ArithmeticExpression(expression1)
                    }
                } else {
                    reportError("Falta ] en la expresión aritmética")
                }
            } else {
                reportError("Falta la expresión aritmética en la expresión aritmética")
            }
        } else {
            val initialPosition = currentPosition
            val number = isNumericValue()
            if (number != null) {
                setNextToken()
                if (currentToken.category == Category.OPERADOR_ARITMETICO) {
                    val operator = currentToken
                    setNextToken()
                    val expression = isArithmeticExpression()
                    if (expression != null) {
                        return ArithmeticExpression(number, operator, expression)
                    }
                } else {
                    return ArithmeticExpression(number)
                }
            } else {
                doBacktracking(initialPosition)
                if (currentToken.category == Category.IDENTIFICADOR) {
                    val identifier = currentToken
                    setNextToken()
                    if (currentToken.category == Category.OPERADOR_ARITMETICO) {
                        val operator = currentToken
                        setNextToken()
                        val expression = isArithmeticExpression()
                        if (expression != null) {
                            return ArithmeticExpression(identifier, operator, expression)
                        }
                    } else {
                        return ArithmeticExpression(identifier)
                    }
                }
            }
        }
        return null
    }

    /**
     * <NumericValue> ::= [<Sign>] Int | [<Sign>] Decimal | [<Sign>] Identifier
     * <Sign> ::=  “'“ | "*"
     * TEST = GREAT
     */
    fun isNumericValue(): NumericValue? {
        val sign: Token? = null
        if ((currentToken.category == Category.OPERADOR_ARITMETICO && currentToken.lexema == "\'") ||
                (currentToken.category == Category.OPERADOR_ARITMETICO && currentToken.lexema == "*")) {
            val sign = currentToken
            setNextToken()
        }
        if (currentToken.category == Category.ENTERO || currentToken.category == Category.IDENTIFICADOR || currentToken.category == Category.DECIMAL) {
            val term = currentToken
            return NumericValue(sign, term)
        }
        return null
    }

    fun isStringExpression(): Expression? {
        return null
    }


    /**
     * <LogicalExpression> ::= <LogicalExpression> LogicalOperator <LogicalExpression> |
     *                      <RelationalExpression> LogicalOperator <RelationalExpression>
     *
     * Equivalent to:
     *
     * <ExpresionLogica> ::= "!" <ExpresionRelacional> | <ExpresionRelacional> || <ExpresionRelacional> |
     * <ExpresionRelacional> && <ExpresionRelacional> | <ExpresionRelacional>
     *
     * <LogicalExpression> ::=  <RelationalExpression> LogicalOperator <RelationalExpression> [ LogicalOperator <LogicalExpression> ] |
     *                          <RelationalExpression> | yas <RelationalExpression> | identifier LogicalOperator identifier [ LogicalOperator <LogicalExpression> ]
     *
     */
    fun isLogicalExpression(): LogicalExpression? {

        if (currentToken.category == Category.OPERADOR_LOGICO && currentToken.lexema == "yas") {
            val operator = currentToken
            setNextToken()
            val relationalExpression = isRelationalExpression()
            if (relationalExpression != null) {
                return LogicalExpression(operator, relationalExpression)
            }
        } else {
            val relationalExpression1 = isRelationalExpression()
            if(relationalExpression1!=null){
                if(currentToken.category == Category.OPERADOR_LOGICO){
                    val operator = currentToken
                    setNextToken()
                    val relationalExpression2 = isRelationalExpression()
                    if (relationalExpression2 != null) {
                        return LogicalExpression(relationalExpression1, operator, relationalExpression2)
                    }
                } else {
                    return LogicalExpression(relationalExpression1)
                }
            }
        }
        return null
    }

    /**
     * <RelationalExpression> ::= <RelationalExpression> RelationalOperator <RelationalExpression>  |
     *                            <ArithmeticExpression> RelationalOperator <ArithmeticExpression>
     *
     * Equivalent to:
     *
     * <RelationalExpression> ::= <ArithmeticExpression> RelationalOperator <ArithmeticExpression> [RelationalOperator <RelationalExpression>]|
     *                              kronos | zeus
     *
     * <ExpresionRelacional> ::= <ExpresionAritmetica> operadorRelacional <ExpresionAritmetica> |
     *                   false |
     *                  true |
     *               expresion
     *
     */
    fun isRelationalExpression():RelationalExpression?{
        if(currentToken.category == Category.PALABRA_RESERVADA && (currentToken.lexema == "kronos"|| currentToken.lexema == "zeus" || currentToken.lexema == "yas")){
            return RelationalExpression(currentToken)
        }else{
            val initialPosition = currentPosition
            val arithmeticExpression = isArithmeticExpression()
            if(arithmeticExpression != null){
                if(currentToken.category == Category.OPERADORES_RELACIONALES){
                    val operator = currentToken
                    setNextToken()
                    val arithmeticExpression2 = isArithmeticExpression()
                    if(arithmeticExpression2 !=null){
                        return RelationalExpression(arithmeticExpression, operator, arithmeticExpression2)
                    }else{
                        reportError("Falta la expresión aritmética 2 en la expresión relacional")
                    }
                }else{
                    reportError("Falta el operador relacional en la expresión relacional")
                }
            }else{
                doBacktracking(initialPosition)
                val expression = isExpression()
                if(expression != null){
                    return RelationalExpression(expression)
                }else{
                    reportError("Falta la expesión en la expresión relacional")
                }
            }
        }
        return null
    }

    /**
     * <DataType> ::= becu | bemol | ante | bridge | pulso | largo
     */
    fun isDataType():Token?{
        if(currentToken.category == Category.PALABRA_RESERVADA){
            if(currentToken.lexema == "becu" || currentToken.lexema=="pulso"
                    ||currentToken.lexema == "largo" || currentToken.lexema == "ante"
                    || currentToken.lexema == "bridge" || currentToken.lexema == "bemol"){
                return currentToken
            }
        }
        return null
    }

    /**
     * <ReturnType> ::= becu | pulso | largo | ante | bridge
     */
    fun isReturnType(): Token? {
        if (currentToken.category == Category.PALABRA_RESERVADA) {
            if (currentToken.lexema == "becu" || currentToken.lexema == "pulso"
                    || currentToken.lexema == "largo" || currentToken.lexema == "ante"
                    || currentToken.lexema == "bridge") {
                return currentToken
            }
        }
        return null
    }

    /**
     * <DeclaracionArreglo> ::= <TipoDato> identificador “{““}” [<InicializacionArreglo>]  “\”
     * <TipoDato> ::= becu | bemol | ante | bridge | pulso | largo
     */
    fun isDeclarationArray(): Token? {

        return null
    }

    /**
     * <InicializacionArreglo> ::= “;” <TipoDato> “{“ identificador “}”  |  “;” <TipoDato> “{“ <Numero> “}”
     * <TipoDato> ::= becu | bemol | ante | bridge | pulso | largo
     *<Número> ::= Entero
     */
    fun isInitializationArray(): Token? {

        return null
    }

    /**
     * <Casting> ::=  <TipoDato> identificador “;” “[“ <TipoDato> “]” identificador “\”  |
     * <TipoDato> identificador “;” “[“ <TipoDato> “]” <ExpresionAritmetica> “\”
     */
    fun isCasting(): Casting? {
        var dataType = isDataType()

        if (dataType != null) {
            setNextToken()
            if (currentToken.category == Category.IDENTIFICADOR) {
                setNextToken()
                if (currentToken.category == Category.DOS_PUNTOS) {
                    setNextToken()
                    if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                        setNextToken()
                        var dataType1 = isDataType()
                        if (dataType1 != null) {
                            setNextToken()
                            if (currentToken.category == Category.PARENTESIS_DERECHO) {
                                setNextToken()
                                var expression = isExpression()
                                if (currentToken.category == Category.IDENTIFICADOR || currentToken != null) {
                                    var name = currentToken
                                    setNextToken()
                                    if (currentToken.category == Category.TERMINAL) {
                                        setNextToken()
                                        return Casting(name, dataType, dataType1, expression)
                                    } else {
                                        reportError("Falta el terminal")
                                    }
                                } else {
                                    reportError("Falta identificador o expresión")
                                }
                            } else {
                                reportError("Falta parentesis derecho")
                            }
                        } else {
                            reportError("falta el tipo de dato")
                        }
                    } else {
                        reportError("Falta parentesis izquierdo")
                    }
                } else {
                    reportError("faltan los dos puntos ;")
                }
            } else {
                reportError("Falta el identificador")
            }
        }
        return null
    }

    /**
     * <Switch> ::=  option “[“ identificador “]” “<” <ListaCasos> “>”
     * <ListaCasos> ::= <Casos> ["_"<ListaCasos>]
     * <Casos> ::=  rast Entero “;”  <Sentencia>  “\”  “,” GP “\” |  rast cadena_caracteres “;”
     * <Sentencia>  “\”  “,” GP “\”  | rast caracter “;” <Sentencia>  “\”  “,” GP “\”
     */
    fun isSwitch(): Switch? {

        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "option") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                if (currentToken.category == Category.IDENTIFICADOR) {
                    var name = currentToken
                    setNextToken()
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        if (currentToken.category == Category.LLAVE_IZQUIERDA) {
                            setNextToken()
                            var caseList = isSwitchCaseList()
                            if (currentToken.category == Category.LLAVE_DERECHA) {

                                setNextToken()
                                return Switch(name, caseList)
                            } else {
                                reportError("Falta la llave derecha")
                            }
                        } else {
                            reportError("falta llave izquierda")
                        }
                    } else {
                        reportError("Falta parentesis derecho")
                    }
                } else {
                    reportError("Falta el identificador")
                }
            } else {
                reportError("Falta parentesis izquierdo")
            }
        }
        return null
    }

    /**
     * <ListaCasos> ::= <Casos> ["_"<ListaCasos>]
     * <Casos> ::=  rast Entero “;”  <Sentencia>  “\”  “,” GP “\” |  rast cadena_caracteres “;”
     * <Sentencia>  “\”  “,” GP “\”  | rast caracter “;” <Sentencia>  “\”  “,” GP “\”
     */
    fun isSwitchCaseList(): ArrayList<Case> {

        var caseList = ArrayList<Case>()
        var case = isCase()

        while (case != null) {
            caseList.add(case)
            if (currentToken.category == Category.SEPARADOR) {
                setNextToken()
            } else if (currentToken.category != Category.LLAVE_DERECHA) {
                reportError("Falta un separador en la lista de argumentos")
            } else {
                break
            }
            case = isCase()
        }
        return caseList
    }

    /**
     * <Casos> ::=  rast Entero “;”  <Sentencia>  “\”  “,” GP “\” |  rast cadena_caracteres “;”
     * <Sentencia>  “\”  “,” GP “\”  | rast caracter “;” <Sentencia>  “\”  “,” GP “\”
     */
    fun isCase(): Case? {

        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "rast") {
            setNextToken()
            var dataType = currentToken
            if (currentToken.category == Category.ENTERO || currentToken.category == Category.CADENA_CARACTERES
                    || currentToken.category == Category.CARACTER) {
                setNextToken()
                if (currentToken.category == Category.DOS_PUNTOS) {
                    setNextToken()
                    var statement = isStatement()
                    if (statement != null) {
                        if (currentToken.category == Category.PUNTO) {
                            setNextToken()
                            if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "GP") {
                                setNextToken()
                                if (currentToken.category == Category.TERMINAL) {
                                    setNextToken()
                                    return Case(dataType, statement)
                                } else {
                                    reportError("Falta el terminal")
                                }
                            } else {
                                reportError("Falta la palabra reservada GP")
                            }
                        } else {
                            reportError("Falta el punto")
                        }
                    } else {
                        reportError("Falta la sentencia")
                    }
                } else {
                    reportError("Faltan los dosnpuntos")
                }
            } else {
                reportError("falta el tipo de dato")
            }
        }
        return null
    }
}