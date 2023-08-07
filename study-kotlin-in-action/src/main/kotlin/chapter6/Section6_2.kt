package chapter6

import java.lang.IllegalStateException

// Int
fun showProgress(progress: Int) {
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done!")
}

// Any
val answer: Any = 42

// Unit 타입
interface Processor<T> {
    fun process(): T
}

class NoResultProcessor: Processor<Unit> {
    override fun process() {
        TODO("Not yet implemented")
    }
}

// Nothing: 결코 정상적으로 끝나지 않음
fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}