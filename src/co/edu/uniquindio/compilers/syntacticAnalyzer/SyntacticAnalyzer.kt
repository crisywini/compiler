package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Category
import co.edu.uniquindio.compilers.lexicalAnalyzer.Error
import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class SyntacticAnalyzer(var tokenList:ArrayList<Token>) {
    var currentPosition = 0
    var currentToken = tokenList[0]
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
            function = isFunction()
        }
        return functionList
    }

    fun reportError(message:String){

    }

    /**
     * <Function> ::= tutti identifier "["[<ParamList>]"]" "<"<StatementBlock>">" <ReturnType> |
     *                  solo identifier "["[<ParamList>]"]" "<"<StatementBlock>">" <ReturnType> |
     *                  proteto identifier "["[<ParamList>]"]" "<"<StatementBlock>">" <ReturnType>
     */
    fun isFunction():Function?{

        if(currentToken.category == Category.PALABRA_RESERVADA && (currentToken.lexema == "tutti" || currentToken.lexema == "solo" || currentToken.lexema == "proteto")){
            setNextToken()
            if(currentToken.category == Category.IDENTIFICADOR ){
                setNextToken()
                if(currentToken.category == Category.PARENTESIS_IZQUIERDO){
                    setNextToken()
                    var paramList =
                    var returnType = isReturnType()
                    if(returnType!=null){
                        setNextToken()
                    }else{
                        reportError("Falta el tipo de ")
                    }
                }

            }

        }
        return null
    }

    fun isParamList(){

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