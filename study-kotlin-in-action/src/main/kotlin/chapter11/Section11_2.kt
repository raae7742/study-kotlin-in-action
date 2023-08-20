package chapter11

fun buildString(
    builderAction: StringBuilder.() -> Unit
): String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}

fun main() {
    val s = buildString {
        append("Hello, ")
        append("World!")
    }
    println(s)
}

val appendExcl : StringBuilder.() -> Unit = { this.append("!") }
