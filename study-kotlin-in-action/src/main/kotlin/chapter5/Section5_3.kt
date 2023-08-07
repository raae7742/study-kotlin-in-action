package chapter5

import java.io.File

fun main() {
    // 시퀀스 만들기
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())

    val file = File("/Users/svtk/.HiddenDir/a.txt")
    println(file.isInsideHiddenDirectory())
}

fun File.isInsideHiddenDirectory() =
    generateSequence(this) { it.parentFile }.any{ it.isHidden }