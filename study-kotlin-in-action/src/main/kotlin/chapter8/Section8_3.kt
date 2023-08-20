package chapter8

// 고차 함수 내 흐름 제어
data class Person1(val name: String, val age: Int)

val people = listOf(Person1("Alice", 29), Person1("Bob", 31))

fun lookForAlice1(people: List<Person1>) {
    people.forEach label@{
        if (it.name == "Alice") return@label
    }

    println("Alice might be somewhere")
}

fun lookForAlice2(people: List<Person1>) {
    people.forEach {
        if (it.name == "Alice") return@forEach
    }

    println("Alice might be somewhere")
}

fun lookForAlice3(people: List<Person1>) {
    people.forEach(fun(person) {
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })
}

fun main() {
    people.filter(fun (person): Boolean {
        return person.age < 30
    })
}

