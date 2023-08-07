package chapter6

import java.io.BufferedReader
import java.lang.NumberFormatException

// nullable한 컬렉션
fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()

    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number)
        } catch (e: NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0

    for (number in numbers) {
        if (number != null) {
            sumOfValidNumbers += number
        } else {
            invalidNumbers++
        }
    }
}

fun addVliadNumbersRefactoring(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum of valid numbers: ${validNumbers.sum()}")
    println("Invalid numbers: ${numbers.size - validNumbers.size}")
}

// 읽기 전용과 변경 가능한 컬렉션
fun <T> copyhElements(source: Collection<T>, target: MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

// 배열
fun main(args: Array<String>) {
    for (i in args.indices) {
        println("Argument $i is: ${args[i]}")
    }

    val letters = Array<String>(26) { i -> ('a' + i).toString()}
    println(letters.joinToString(""))

    val strings = listOf("a", "b", "c")
    println("%s/%s/%s".format(*strings.toTypedArray()))

    args.forEachIndexed { index, element ->
        println("Argument $index is: $element")
    }
}