package chapter9

import java.lang.Appendable

// 제네릭스
fun main() {
    val authors = listOf("Dmitry", "Svetlana")
    val readers = mutableListOf<String>(/* ... */)
    readers.filter { it !in authors }
}

// 타입 파라미터 제약
fun <T: Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T)
where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}