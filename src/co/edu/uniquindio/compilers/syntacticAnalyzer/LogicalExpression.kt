package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class LogicalExpression():Expression() {
    var relationalExpression1:RelationalExpression? = null
    var relationalExpression2:RelationalExpression? = null
    var logicalOperator1:Token? = null
    var logicalOperator2:Token? = null
    var identifier:Token? = null
    var identifier2:Token? = null
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
    constructor(relationalExpression1: RelationalExpression?):this(){
        this.relationalExpression1 = relationalExpression1
    }
    constructor(logicalOperator1:Token?,relationalExpression1: RelationalExpression?):this(){
        this.logicalOperator1 = logicalOperator1
        this.relationalExpression1 = relationalExpression1
    }
    constructor(identifier:Token?):this(){
        this.identifier = identifier
    }
    //     return LogicalExpression(identifier, operator, identifier2, operator2, logicalExpression)
    constructor(identifier: Token?, logicalOperator1: Token?, identifier2: Token?, logicalOperator2: Token?,logicalExpression:LogicalExpression?):this(){
        this.identifier = identifier
        this.logicalExpression = logicalExpression
        this.logicalOperator1 = logicalOperator1
        this.logicalOperator2 = logicalOperator2
        this.identifier2 = identifier2
    }
//                            return LogicalExpression(identifier, operator, identifier2)
    constructor(identifier: Token?, logicalOperator1: Token?, identifier2: Token?):this(){
        this.identifier = identifier
        this.logicalOperator1 = logicalOperator1
        this.identifier2 = identifier2
    }
    override fun toString(): String {
        return "LogicalExpression(relationalExpression1=$relationalExpression1, relationalExpression2=$relationalExpression2, logicalOperator1=$logicalOperator1, logicalOperator2=$logicalOperator2, logicalExpression=$logicalExpression)"
    }

}