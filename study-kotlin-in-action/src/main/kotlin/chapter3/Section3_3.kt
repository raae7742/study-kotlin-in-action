package chapter3

fun main() {
    println("Kotlin".lastChar())
}

// 확장 함수
fun String.lastChar(): Char = this.get(this.length - 1)

/** 오버라이드
 * val view: View = Button()
 * view.click()
 * => 하위 클래스의 함수 호출
 */
open class View {
    open fun click() = println("view cliced")
}

class Button: View() {
    override fun click() = println("Button clicked")
}

/** 확장 함수
 * val view: View = Button()
 * view.showOff()
 * => 정적으로 결정된 View의 확장 함수 수행
 */
fun View.showOff() = println("I'm a view!")
fun Button.showOff() = println("I'm a button!")

// 확장 프로퍼티
val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }