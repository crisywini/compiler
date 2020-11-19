package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class LogicalExpression():Expression() {
    var relationalExpression1:RelationalExpression? = null
    var relationalExpression2:RelationalExpression? = null
    var logicalOperator1:Token? = null
    var logicalOperator2:Token? = null
    var logicalExpression:LogicalExpression? = null

    constructor(relationalExpression1: RelationalExpression?, logicalOperator1:Token?,
                relationalExpression2: RelationalExpression?, logicalOperator2: Token?,
                logicalExpression: LogicalExpression?):this(){
        this.relationalExpression1 = relationalExpression1
        this.relationalExpression2 = relationalExpression2
        this.logicalOperator1 = logicalOperator1
        this.logicalOperator2 = logicalOperator2
        this.logicalExpression = logicalExpression
    }
    constructor(relationalExpression1: RelationalExpression?, logicalOperator1: Token?, relationalExpression2: RelationalExpression?):this(){
        this.relationalExpression1 = relationalExpression1
        this.logicalOperator1 = logicalOperator1
        this.relationalExpression2 = relationalExpression2
    }
}