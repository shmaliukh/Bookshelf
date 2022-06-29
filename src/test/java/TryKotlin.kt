fun main() {
    var v1 = MyInt(10)// 2
    println(v1 + 10);
    println("Hello, World!")        // 3
}

class MyInt(val arg1: Int){
    operator fun plus(arg2: Int) {
        arg1+arg2*2;
    }
}