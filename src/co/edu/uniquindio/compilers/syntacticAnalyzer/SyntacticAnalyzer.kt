package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Category
import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.ErrorCategory
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class SyntacticAnalyzer(var tokenList:ArrayList<Token>) {
    var currentPosition = 0
    var currentToken:Token = tokenList[0]
    var errorList = ArrayList<Error>()

    fun setNextToken(){
        currentPosition ++
        if(currentPosition<tokenList.size){
            currentToken = tokenList[currentPosition]
        }
    }

    /**
     * <CompilationUnit> ::= [<VariableDeclarationList> ] <FunctionsList>
     */
    fun isCompilationUnit():CompilationUnit?{
        val variableDeclarationList:ArrayList<VariableDeclaration> = isVariableDeclarationList()
        val functionsList:ArrayList<Function> = isFunctionList()
        return if(functionsList.size>0){
            CompilationUnit(functionsList, variableDeclarationList)
        }else null
    }

    /**
     * <VariableDeclarationList> ::= <VariableDeclaration>[<VariableDeclarationList>]
     */
    fun isVariableDeclarationList():ArrayList<VariableDeclaration>{
        var variableDeclarationList = ArrayList<VariableDeclaration>()
        var variableDeclaration = isVariableDeclaration()
        while(variableDeclaration!=null){
            variableDeclarationList.add(variableDeclaration)
            if(currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti"){
                break
            }
            variableDeclaration = isVariableDeclaration()
        }
        return variableDeclarationList
    }

    /**
     * <VariableDeclaration> ::= <MutableVariableDeclaration>|<ImmutableVariableDeclaration>
     */
    fun isVariableDeclaration():VariableDeclaration?{

        var variableDeclaration = isMutableVariableDeclaration()
        return if(variableDeclaration!=null){
            variableDeclaration
        }else{
            variableDeclaration = isImmutableVariableDeclaration()
            variableDeclaration
        }
    }

    /**
     * <MutableVariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
     */
    fun isMutableVariableDeclaration():VariableDeclaration? {
        val dataType = isDataType()
        if(dataType !=null){
            setNextToken()
            if(currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti"){
                setNextToken()
                if(currentToken.category == Category.DOS_PUNTOS){
                    setNextToken()
                    val identifierList = isIdentifierList()
                    if(identifierList.size > 0){
                        if(currentToken.category == Category.TERMINAL){
                            setNextToken()
                            return VariableDeclaration(dataType,identifierList)
                        }else{
                            reportError("Falta el terminal \\")
                        }
                    }else{
                        reportError("Faltan los identificadores")
                    }
                }else{
                    reportError("Faltan ;")
                }
            }else{
                reportError("Falta la palabra reservada")
            }
        }else{
            reportError("Falta el tipo de dato")
        }
        return null
    }

    /**
     *  <ImmutableVariableDeclaration> ::= <DataType> solo ";" <IdentifiersList> " \ "
     */
    fun isImmutableVariableDeclaration():VariableDeclaration?{
        val dataType = isDataType()
        if(dataType !=null){
            setNextToken()
            if(currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "solo"){
                setNextToken()
                if(currentToken.category == Category.DOS_PUNTOS){
                    setNextToken()
                    val identifierList = isIdentifierList()
                    if(identifierList.size > 0){
                        if(currentToken.category == Category.TERMINAL){
                            setNextToken()
                            return VariableDeclaration(dataType,identifierList)
                        }else{
                            reportError("Falta el terminal \\")
                        }
                    }else{
                        reportError("Faltan los identificadores")
                    }
                }else{
                    reportError("Faltan ;")
                }
            }else{
                reportError("Falta la palabra reservada")
            }
        }else{
            reportError("Falta el tipo de dato")
        }
        return null
    }

    /**
     * <IdentifierList> ::= identifier ["_"<IdentifierList>]
     */
    fun isIdentifierList():ArrayList<Token>{
        var identifierList = ArrayList<Token>()
        var identifier:Token ?
        if(currentToken.category == Category.IDENTIFICADOR){
            identifier = currentToken
            setNextToken()
        }else{
            identifier = null
        }
        while(identifier != null){
            identifierList.add(identifier)
            if (currentToken.category == Category.SEPARADOR){
                setNextToken()
                identifier = if(currentToken.category == Category.IDENTIFICADOR){
                    setNextToken()
                    currentToken
                }else{
                    null
                }
            }else if(currentToken.category == Category.TERMINAL){
                break
            }else{
                reportError("Falta separador")
                break
            }
        }
        return identifierList
    }

    /**
     * <FunctionsList> ::= <Function>[<FunctionsList>]
     */
    fun isFunctionList():ArrayList<Function>{
        var functionList = ArrayList<Function>()
        var function = isFunction()

        while(function!=null){
            functionList.add(function)
            if(currentPosition==tokenList.size){
                break
            }
            function = isFunction()
        }
        return functionList
    }

    fun reportError(message:String){
        errorList.add(Error(message, currentToken.row, currentToken.column, ErrorCategory.ERROR_SINTACTICO))
    }

    /**
     * <Function> ::= tutti identifier "["[<ParamList>]"]" <StatementBlock> <ReturnType>
     *
     */
    fun isFunction():Function?{
        if(currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti" ){
            setNextToken()
            if(currentToken.category == Category.IDENTIFICADOR ){
                var identifier = currentToken
                setNextToken()
                if(currentToken.category == Category.PARENTESIS_IZQUIERDO){
                    setNextToken()
                    var paramList = isParamList()
                    if(currentToken.category == Category.PARENTESIS_DERECHO){
                        setNextToken()
                        var statementBlock = isStatementBlock()
                        if(statementBlock != null){
                            var returnType = isReturnType()
                            if(returnType!=null){
                                setNextToken()
                                //This function is correct
                                return Function(identifier,returnType, paramList, statementBlock)
                            }else{
                                reportError("El tipo de retorno está vacio")
                            }
                        }else{
                            reportError("Falta el bloque de sentencias ")
                        }
                    }else{
                        reportError("Falta el paréntesis derecho")
                            }
                        }else{
                            reportError("Falta el paréntesis izquierdo")
                        }
                    }else{
                        reportError("Falta el identificador")
                    }
                }else{
                    reportError("Falta la palabra reservada")
                }
            return null
        }

    /**
     * <Desicion> ::= eva “[“ <LogicalExpression> “]” <StatementBlock>  [contra  <StatementBlock>]
     *
     */

    fun isDecision():Decision? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "eva") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                var expression = isLogicalExpression()
                if (expression != null) {
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        var statementBlock = isStatementBlock()
                        if(statementBlock != null){
                            if (currentToken.lexema == "contra") {
                                setNextToken()
                                var statementBlock2 = isStatementBlock()
                                if(statementBlock2 != null) {
                                    return Decision(expression, statementBlock,statementBlock2)
                                }
                            } else{
                                return Decision(expression, statementBlock,null)
                            }
                        }else{
                            reportError("Falta bloque de sentencias")
                        }
                    }else{
                        reportError("Falta parentesis derecho")
                    }
                }else{
                    reportError("Falta la expresion")
                }
            }else{
                reportError("Falta parentesis izquierdo")
            }
        }
        return null;
    }

    /**
     * <CycleWhile>::= Rondo “[” <logicalExpression> “]” <StatementBlock>
     *
     */
    fun isCycle():Cycle? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "rondo") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                var expression = isLogicalExpression()
                if (expression != null) {
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        var statementBlock = isStatementBlock()
                        if(statementBlock != null) {
                            return Cycle(expression, statementBlock)
                        }else{
                            reportError("Falta bloque de sentencias")
                        }
                    }else{
                        reportError("Falta parentesis derecho")
                    }
                }else{
                    reportError("Falta la expresion del ciclo")
                }
            }else{
                reportError("Falta parentesis izquierdo")
            }
        }else{
            reportError("Falta la palabra reservada")
        }
        return null
    }

    /**
     * <Read>::= Proteto “[“ identifier “]” “\”
     *
     */
    fun isRead():Read? {
        if (currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "proteto") {
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                if(currentToken.category == Category.IDENTIFICADOR ) {
                    var identifier = currentToken
                    setNextToken()
                    if (currentToken.category == Category.PARENTESIS_DERECHO) {
                        setNextToken()
                        if(currentToken.category == Category.TERMINAL){
                            setNextToken()
                            return Read(identifier)
                        }else{
                            reportError("Falta el terminal")
                        }
                    }else{
                        reportError("Falta parentesis derecho")
                    }
                }else{
                    reportError("Falta el identificador")
                }
            }else{
                reportError("Falta parentesis izquierdo")
            }
        }else{
            reportError("Falta palabra reservada")
        }
        return null
    }
    /**
     * <FunctionInvocation>::= identifier “[“ [<argumentList>] “]” “\”
     * Donde está el proyecto? en escritorio 
     */
    fun isFunctionInvocation():FunctionInvocation? {

        if(currentToken.category == Category.IDENTIFICADOR ) {
            var identifier = currentToken
            setNextToken()
            if (currentToken.category == Category.PARENTESIS_IZQUIERDO) {
                setNextToken()
                var argumentList = isArgumentList()
                if(currentToken.category == Category.PARENTESIS_DERECHO) {
                    setNextToken()
                    if(currentToken.category == Category.TERMINAL){
                        setNextToken()
                        return FunctionInvocation(identifier,argumentList)
                    }else{
                        reportError("Falta el terminal")
                    }
                }else{
                    reportError("Falta el parentesis derecho")
                }
            }else{
                reportError("Falta el parentesis izquierdo")
            }
        }else{
            reportError("Falta el identificador")
        }
        return null
    }
    /**
     * <Increment>::= identifier IncrementOperator “\”
     *
     */
    fun isIncrement():Increment? {

        if(currentToken.category == Category.IDENTIFICADOR ) {
            var identifier = currentToken
            setNextToken()
            if(currentToken.category == Category.OPERADOR_INCREMENTO ) {
                setNextToken()
                if(currentToken.category == Category.TERMINAL){
                    setNextToken()
                        return Increment(identifier)
                }else{
                    reportError("Falta el terminal")
                }
            }else{
                reportError("Falta el operador de incremento")
            }
        }else{
            reportError("Falta el identificador")
        }
        return null
    }
    /**
     * <Decrement>::= identifier DecrementOperator “\”
     *
     */
    fun isDecrement():Decrement? {

        if(currentToken.category == Category.IDENTIFICADOR ) {
            var identifier = currentToken
            setNextToken()
            if(currentToken.category == Category.OPERADOR_DECREMENTO ) {
                setNextToken()
                if(currentToken.category == Category.TERMINAL){
                    setNextToken()
                    return Decrement(identifier)
                }else{
                    reportError("Falta el terminal")
                }
            }else{
                reportError("Falta el operador de decremento")
            }
        }else{
            reportError("Falta el identificador")
        }
        return null
    }

    /**
     * <parametro> ::= <TipoDato> identificador
     * <TipoDato> ::= becu | bemol | ante | bridge | pulso | largo
     */
    fun isParam():Param?{

        val dataType = isDataType()
        val name = currentToken
        if(dataType != null) {
            setNextToken()
            if (currentToken.category == Category.IDENTIFICADOR) {
                setNextToken()

                return Param(name, dataType)
            } else {
                reportError("Falta el tipo de parámetro")
            }
        } else {
            reportError("Falta el tipo de dato")
        }
        return null
    }


    /**
    * <ParamList> ::= <Param>|["_"<ParamList>]
     */
    fun isParamList():ArrayList<Param>{//Debe retornar una lista de parametros
        var paramList = ArrayList<Param>()
        var param = isParam()

        while(param!=null){
            paramList.add(param)
            if(currentToken.category==Category.SEPARADOR){
                setNextToken()
                param = isParam()
            } else {
                reportError("Falta un separador en la lista de parámetros")
                break
            }
            param = isParam()
        }
        return paramList
    }
    /**
     * <argument> ::= identificador | <Expression>
     */
    fun isArgument():Argument?{
        return null
    }
    /**
     * <argumentList> ::= <argument>|["_"<argumentList>]
     */
    fun isArgumentList(): ArrayList<Argument>? {
        return null
    }

    /**
     * <StatementBlock> ::= "<"[<StatementList>]">"
     */
    fun isStatementBlock():ArrayList<Statement>?{//Debe retornar un bloque de sentencias
        if(currentToken.category == Category.LLAVE_IZQUIERDA){
            setNextToken()
            var statementList = isStatementList()
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
    fun isStatementList():ArrayList<Statement>{
        val statementList = ArrayList<Statement>()
        var statement = isStatement()
        while(statement != null){
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

        if(statement != null){
            return statement
        }

        statement = isDecision()
        if(statement != null){
            return statement
        }
        statement = isAssignment()
        if(statement != null){
            return statement
        }
        statement = isCycle()
        if(statement != null){
            return statement
        }
        statement = isRead()
        if(statement != null){
            return statement
        }
        statement = isFunctionInvocation()
        if(statement != null){
            return statement
        }
        statement = isIncrement()
        if(statement != null){
            return statement
        }
        statement = isDecrement()
        if(statement != null){
            return statement
        }


        return null
    }

    /**
     * <Assignment>::= Identifier AssignmentOperator <Expression> “\”
     */
    fun isAssignment():Assignment?{
        if(currentToken.category == Category.IDENTIFICADOR){
            var identifier = currentToken
            setNextToken()
            if(currentToken.category == Category.OPERADOR_ASIGNACION){
                setNextToken()
                var expression = isExpression()
                if(expression !=null){
                    setNextToken()
                    if(currentToken.category == Category.TERMINAL){
                        setNextToken()
                        return Assignment(identifier, expression)
                    }else{
                        reportError("Falta el terminal")
                    }
                }else{
                    reportError("Falta la expresión")
                }
            }else{
                reportError("Falta el operador de asignación")
            }
        }else{
            reportError("Falta el identificador")
        }
        return null
    }

    /**
     * <Expression> ::= <ArithmeticExpression> | <RelationalExpression> | <LogicalExpression> | <StringExpression>
     */
    fun isExpression():Expression?{
        return null
    }

    /**
     * <LogicalExpression> ::= <LogicalExpression> OperadorLogico <LogicalExpression> | <RelationalExpression> OperadorLogico <RelationalExpression>
     */
    fun isLogicalExpression():LogicalExpression?{
        return null
    }

    /**
     * <DataType> ::= becu | bemol | ante | bridge | pulso | largo
     */
    fun isDataType():Token?{
        if(currentToken.category == Category.PALABRA_RESERVADA){
            if(currentToken.lexema == "becu" || currentToken.lexema=="pulso"
                    ||currentToken.lexema == "largo" || currentToken.lexema == "ante"
                    || currentToken.lexema == "bridge"){
                return currentToken
            }
        }
        return null
    }

    /**
     * <ReturnType> ::= becu | pulso | largo | ante | bridge
     */
    fun isReturnType():Token?{
        if(currentToken.category == Category.PALABRA_RESERVADA){
            if(currentToken.lexema == "becu" || currentToken.lexema=="pulso"
                    ||currentToken.lexema == "largo" || currentToken.lexema == "ante"
                    || currentToken.lexema == "bridge"){
                return currentToken
            }
        }
        return null
    }
}