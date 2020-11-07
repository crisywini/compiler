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
     * <CompilationUnit> ::= <FunctionsList>
     */
    fun isCompilationUnit():CompilationUnit?{
        val functionsList:ArrayList<Function> = isFunctionList()
        return if(functionsList.size>0){
            CompilationUnit(functionsList)
        }else null
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
     * <Function> ::= tutti identifier "["[<ParamList>]"]" <StatementBlock> <ReturnType> |
     *                  solo identifier "["[<ParamList>]"]" <StatementBlock> <ReturnType> |
     *                  proteto identifier "["[<ParamList>]"]" <StatementBlock> <ReturnType>
     */
    fun isFunction():Function?{
        if(currentToken.category == Category.PALABRA_RESERVADA && (currentToken.lexema == "tutti" || currentToken.lexema == "solo" || currentToken.lexema == "proteto")){
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