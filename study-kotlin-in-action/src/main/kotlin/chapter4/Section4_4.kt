package chapter4

import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File

/** 객체 선언: 싱글턴 */
object Payroll {
    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {
            //...
        }
    }
}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(f1: File, f2: File): Int {
        return f1.path.compareTo(f2.path, ignoreCase = true)
    }
}

data class Person(val name: String) {
    // 클래스 안에서 객체를 정의하는 게 더 바람직
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int =
            p1.name.compareTo(p2.name)
    }
}

/** 동반 객체 */
class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

private fun getFacebookName(facebookAccountId: Int): String {
    //...
    return ""
}

class User6 {
    val nickname: String

    constructor(email: String) {
        nickname = email.substringBefore('@')
    }

    constructor(facebookAccountId: Int) {
        nickname = getFacebookName(facebookAccountId)
    }
}

class User7 private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
            User7(email.substringBefore('@'))

        fun newFacebookUser(accountId: Int) =
            User7(getFacebookName(accountId))
    }
}

/** 동반 객체를 일반 객체처럼 사용 */
class Person1(val name: String) {
    // Default name: Companion
    companion object Loader {
        fun fromJSON(jsonText: String): Person = Person(jsonText)
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person2(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = Person(jsonText)
    }
}

// 비즈니스 로직 모듈
class Person3(val firstName: String, val lastName: String) {
    companion object {

    }
}

// 클라이언트/서버 통신 모듈
fun Person3.Companion.fromJSON(json: String): Person {
    return Person(json)
}

/** 객체 식: 무명 내부 클래스를 다른 방식으로 작성 */
val listener = object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) = super.mouseClicked(e)
    override fun mouseEntered(e: MouseEvent?) = super.mouseEntered(e)
}

fun countClicks(window: Window) {
    var clickCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }
    })
}