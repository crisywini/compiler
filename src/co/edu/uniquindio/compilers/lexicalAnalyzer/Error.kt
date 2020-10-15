package co.edu.uniquindio.compilers.lexicalAnalyzer

class Error(var error:String, var row:Int, var column:Int, var errorCategory: ErrorCategory) {
    override fun toString(): String {
        return "Error(error='$error', row=$row, column=$column, errorCategory=$errorCategory)"
    }
}