package chapter5

import java.awt.Button

/** 람다 */
data class Person(val name: String, val age: Int)

fun findTheOldest(people: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun findTheOldestWithLambda(people: List<Person>) {
    println(people.maxBy { it.age })
    println(people.maxBy(Person::age))
    println(people.maxBy { p: Person -> p.age })
}

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")
    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0

    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }

    println("$clientErrors client errors, $serverErrors server errors")
}

/**
 * 비동기적 또는 이벤트 핸들러로 람다를 활용하는 예시
 * - 다음 코드는 버튼 클릭 횟수를 제대로 셀 수 없다.
 */
fun tryToCountButtonClicks(button: Button): Int {
    var clicks = 0
    button.addActionListener { clicks++ }
    return clicks
}

/** 멤버 참조 */
fun salute() = println("Salute!")

fun main() {
    // 전역 함수 참조
    run(::salute)

    // 생성자 참조
    val createPerson = ::Person
    val p = createPerson("Alice", 29)
    println(p)

    //바운드 멤버 참조
    val dmitrysAgeFunction = p::age
    println(dmitrysAgeFunction())
}

