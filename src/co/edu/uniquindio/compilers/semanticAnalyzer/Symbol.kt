package co.edu.uniquindio.compilers.semanticAnalyzer

class Symbol() {

    var name:String = ""
    var type:String = ""
    var modifiable:Boolean = false
    var ambit:String? = ""
    var row:Int = 0
    var column:Int = 0
    var paramTypes:ArrayList<String>? = null

    /**
     * Constructor to create a symbol of type value
     */
    constructor(name:String, dataType:String, modifiable:Boolean, ambit:String, row:Int, column:Int){
        this.name = name
        this.type = dataType
        this.modifiable = modifiable
        this.ambit = ambit
        this.row = row
        this.column = column
    }

    /**
     * Constructor to create a symbol of type method (function)
     */
    constructor(name:String, returnType:String, paramTypes:ArrayList<String>, ambit:String){
        this.name = name
        this.type = returnType
        this.paramTypes = paramTypes
        this.ambit = ambit
    }

    override fun toString(): String {
        return if(paramTypes==null) {
            "Symbol(name='$name', type='$type', modifiable=$modifiable, ambit=$ambit, row=$row, column=$column)"
        } else {
            "Symbol(name='$name', type='$type', column=$column, paramTypes=$paramTypes)"
        }
    }
}