package co.edu.uniquindio.compilers.test

fun main(){

    var number = 12
    number++

    var list = ArrayList<Int>()

    list.add(12)
    for ((i,v) in list.withIndex()){
        println("posición ${i} el valor es : ${v} ")
    }

    println(isPrime(8191))
    println(isPrime(8191,2))
    println(operate(1,2, ::multiply))
    var matrix = arrayOf(arrayOf(1,2,3,4), arrayOf(2,3,4,5))
    var matrix2 = Array(3){IntArray(4)}
    var str:String

}
fun isPrime(number:Int):Boolean{

    for(i in 2 .. number/2 step(1)){
        if(number%i==0){
            return false
        }
    }
    return true
}

fun isPrime(number:Int, i:Int):Boolean{
    return if(number/2<i){
         true
    }else {
        if (number % i == 0) {
            false
        } else {
            isPrime(number, i + 1)
        }
    }
}
/*
Functional programming dandola toda
 */
fun operate(a:Int, b:Int, fn:(Int, Int)->Int):Int{
    return fn(a,b)
}
fun multiply(a:Int, b:Int):Int = a*b
fun divide(a:Int, b:Int):Int = a/b