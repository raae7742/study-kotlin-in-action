package chapter5

fun postponeComputation(delay: Int, computation: Runnable) {}

val runnable = Runnable { println(42) }
fun handleComputation() {
    postponeComputation(1000, runnable)
}

fun main() {
    //SAM 생성자
    createAllDoneRunnable().run()
}

fun createAllDoneRunnable() : Runnable {
    return Runnable { println("All done!") }
}