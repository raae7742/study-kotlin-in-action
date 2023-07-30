package chapter3

class Section3_1 {
    fun main() {
        // 컬렉션 만들기
        val set = hashSetOf(1, 7, 53)
        val list = arrayListOf(1, 7, 53)
        val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

        println(list.joinToString("", "", "."))
        println(list.joinToString(separator = "", prefix = "", postfix = "."))
    }
}

// 최상위 프로퍼티
var opCount = 0
const val UNIX_LINE_SEPARATOR = "\n"

fun performOperation() {
    opCount++
}

fun reportOperationCount() {
    println("Operation performed $opCount times")
}

