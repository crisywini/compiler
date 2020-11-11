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
     * <VariableDeclaration> ::= <DataType> tutti ";" <IdentifiersList> " \ "
     */
    fun isVariableDeclaration():VariableDeclaration?{

        var dataType = isDataType()
        if(dataType !=null){
            setNextToken()
            if(currentToken.category == Category.PALABRA_RESERVADA && currentToken.lexema == "tutti"){
                setNextToken()
                if(currentToken.category == Category.DOS_PUNTOS){
                    setNextToken()
                    var identifierList = isIdentifierList()
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



    fun isParamList():ArrayList<Param>{//Debe retornar una lista de parametros
        return ArrayList()
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
    fun isStatementList():ArrayList<Statement>{
        return ArrayList()
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