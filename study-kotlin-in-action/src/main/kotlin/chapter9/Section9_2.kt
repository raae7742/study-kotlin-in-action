package chapter9

// 소거된 타입 파라미터, 실체화된 타입 파라미터
fun printSum1(c: Collection<*>) {
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

fun printSum2(c: Collection<Int>) {
    if (c is List<Int>) {
        print(c.sum())
    }
}

// 실체화된 타입 파라미터
inline fun <reified T> isA(value: Any) = value is T

inline fun <reified T> Iterable<*>.filterIsInstance(): List<T> {
    val destination = mutableListOf<T>()

    for (element in this) {
        if (element is T) {
            destination.add(element)
        }
    }
    return destination
}

fun main() {
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())
}