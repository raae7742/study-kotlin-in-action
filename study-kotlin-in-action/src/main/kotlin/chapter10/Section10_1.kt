package chapter10

import com.sun.jdi.Value
import java.util.Date
import kotlin.reflect.KClass

// 어노테이션을 활용해 제이키드 라이브러리 구현하기
data class Person (
    @JsonName("alias") val firstName: String,
    @JsonExclude val age: Int? = null
)

annotation class JsonName(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding

interface Company {
    val name: String
}
data class CompanyImpl(override val name: String) : Company

data class Person1(
    val name: String,
    @DeserializeInterface(CompanyImpl::class) val company: Company
)

annotation class DeserializeInterface(val targetClass: KClass<out Any>)

interface ValueSerializer<T> {
    fun toJsonValue(value: T) : Any?
    fun fromJsonValue(jsonValue: Any?) : T
}

data class Person2(
    val name: String,
    @CustomSerializer(DateSerializer::class) val birthdayDate: Date
)

class DateSerializer : ValueSerializer<Date> {
    override fun toJsonValue(value: Date): Any? {
        TODO("Not yet implemented")
    }

    override fun fromJsonValue(jsonValue: Any?): Date {
        TODO("Not yet implemented")
    }

}

annotation class CustomSerializer(
    val serializerClass: KClass<out ValueSerializer<*>>
)