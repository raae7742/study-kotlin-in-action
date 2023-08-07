package chapter5

import java.io.File

/** 컬렉션 함수형 API */
data class Person1(val name: String, val age: Int)

fun main() {
    val canBeInClub27 = { p: Person1 -> p.age <= 27 }
    val people = listOf(Person1("Alice", 27), Person1("Bob", 31))

    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27))
    println(people.find(canBeInClub27))
    println(people.groupBy { it.age })

    val list = listOf("a", "ab", "b")
    println(list.groupBy(String::first))

    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })

    // 지연 연산 컬렉션 연산
    people.asSequence()
        .map(Person1::name)
        .filter { it.startsWith("A") }
        .toList()

    listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList()
}