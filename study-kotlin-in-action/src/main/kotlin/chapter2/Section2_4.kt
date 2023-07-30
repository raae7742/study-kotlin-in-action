package chapter2

import java.util.TreeMap

fun main() {
    // 일반적인 for문
    for (i in 1..100) {
        print(fizzBuzz(i))
    }

    for (i in 100 downTo 1 step 2) {
        print(fizzBuzz(i))
    }

    // map에 대한 for문
    val binaryReps = TreeMap<Char, String>()
    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for ((letter, binary) in binaryReps) {
        println("$letter = $binary")
    }

    // 컬렉션에 대한 for문
    var list = arrayListOf("10", "11", "1001")
    for ((index, element) in list.withIndex()) {
        println("$index: $element")
    }
}

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}


// in: 컬렉션, 범위의 원소 검사
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know..."
}