package chapter2

import chapter2.Color.*

fun main() {
    println(BLUE.rgb())
}

// enum
enum class Color (
    val r: Int, val g: Int, val b: Int
){
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}

// when
fun getMnemonic(color: Color) {
    when (color) {
        RED -> "Richard"
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        BLUE -> "Battle"
        INDIGO -> "In"
        VIOLET -> "Vain"
    }
}

fun getWarmth(color: Color) = when(color) {
    RED, ORANGE, YELLOW -> "warm"
    GREEN -> "neutral"
    BLUE, INDIGO, VIOLET -> "cold"
}

fun mix(c1: Color, c2: Color) =
    when(setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("Dirty Color")
    }

fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW || c1 == YELLOW && c2 == RED) -> ORANGE
        (c1 == YELLOW && c2 == BLUE || c1 == BLUE && c2 == YELLOW) -> GREEN
        (c1 == BLUE && c2 == VIOLET || c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty Color")
    }

// smart cast & if
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval1(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }

    if (e is Sum) {
        return eval1(e.right) + eval1(e.left)
    }

    throw IllegalArgumentException("Unknown expression")
}

fun eval2(e: Expr): Int =
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval2(e.right) + eval2(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }

fun eval3(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval3(e.right) + eval3(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }