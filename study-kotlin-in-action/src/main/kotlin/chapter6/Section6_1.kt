package chapter6

import java.lang.IllegalArgumentException

fun strLenSafe(s: String?): Int =
    if (s != null) s.length else 0

// 안전한 호출 연산자 ?.
class Employee(val name: String, val manager: Employee?)
fun managerName(employee: Employee): String? = employee.manager?.name

class Address(val streetAddress: String, val zipCode: Int,
                val city: String, val country: String)
class Company(val name: String, val address: Address?)
class Person(val name: String, val company: Company?)

fun Person.countryName(): String {
    return company?.address?.country ?: "Unknown"
}

// 엘비스 연산자 ?:
fun printShippingLabel(person: Person) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address")

    with (address) {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

// 안전한 캐스트 as?
class Person1(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person1 ?: return false

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}

// 널 아님 단언 !!
fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

// let 함수
fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

fun main() {
    var email: String? = "yole@example.com"
    email?.let { sendEmailTo(it) }
}

// 타입 파라미터의 널 가능성
fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

fun <T: Any> printHashCodeNotNull(t: T) {
    println(t.hashCode())
}