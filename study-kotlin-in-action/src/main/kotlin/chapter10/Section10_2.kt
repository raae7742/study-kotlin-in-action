package chapter10
import chapter9.filterIsInstance
import com.sun.beans.introspect.ClassInfo
import java.io.Reader
import java.lang.IllegalStateException
import java.lang.reflect.Type
import javax.swing.text.html.parser.Parser
import kotlin.reflect.*

// 리플렉션 API
class Person3(val name: String, val age: Int)

fun foo(x: Int) = println(x)

fun sum(x: Int, y: Int) = x + y

var counter = 0

fun main() {
    val person = Person("Alice", 29)
    val kClass = person.javaClass.kotlin

    println(kClass.simpleName)
    kClass.members.forEach {
        println(it.name)
    }


    val kFunction1 = ::foo
    kFunction1.call(42)


    val kFunction2: KFunction2<Int, Int, Int> = ::sum
    println(kFunction2.invoke(1, 2) + kFunction2(3, 4))

    // kFunction2(1)            // 인자 개수, 타입이 맞지 않으면 컴파일 X


    val kProperty = ::counter
    kProperty.setter.call(0)
    println(kProperty.get())


    val memberProperty = Person::age
    println(memberProperty.get(person))
}


// 제이키드 마져 구현하기
private fun StringBuilder.serializeObject(obj: Any) {
    obj.javaClass.kotlin.members
        .filter { it.findAnnotation<JsonExclude>() == null }
        .joinToStringBuilder(
        this,
        prefix = "{",
        postfix = "}") { it ->
            serializeProperty(it, obj)
        }
    }

private fun StringBuilder.serializeProperty(
    prop: KProperty1<Any, *>,
    obj: Any
) {
    val name = prop.findAnnotation<JsonName>()?.name ?: prop.name
    serializeString(propName)
    append(": ")

    val value = prop.get(obj)
    val jsonValue = prop.getSerializer()?.toJsonValue(value) ?: value
    serializePropertyValue(jsonValue)
}

fun serialize(obj: Any): String = buildString { serializeObject(obj) }

inline fun <reified T> KAnnotatedElement.findAnnotation() : T?
= annotations.filterIsInstance<T>().firstOrNull()


fun KProperty<*>.getSerializer(): ValueSerializer<Any?>? {
   val customSerializerAnn = findAnnotation<CustomSerializer>() ?: return null
   val serializerClass = customSerializerAnn.serializerClass
   val valueSerializer = serializerClass.objectInstance
       ?: serializerClass.createInstance()

   @Suppress("UNCHECKED_CAST")
   return valueSerializer as ValueSerializer<Any?>
}

// 객체 역직렬화
inline fun <reified T: Any> deserialize(json: String) : T {}

interface JsonObject {
    fun setSimpleProperty(propertyName: String, value: Any?)
    fun createObject(propertyName: String): JsonObject
    fun createArray(propertyName: String): JsonObject
}

interface Seed: JsonObject {
    fun spawn(): Any?
    fun createCompositeProperty(
        propertyName: String,
        isList: Boolean
    ): JsonObject

    override fun createObject(propertyName: String): JsonObject = createCompositeProperty(propertyName, false)
    override fun createArray(propertyName: String): JsonObject = createCompositeProperty(propertyName, true)
}

fun <T: Any> deserialize(json: Reader, targetClass: KClass<T>): T {
    val seed = ObjectSeed(targetClass, ClassInfoCache())
    Parser(json, seed).parse()
    return seed.spawn()
}

class ObjectSeed<out T: Any?> (
    targetClass: KClass<T>,
    val classInfoCache: ClassInfoCache
): Seed {
    private val classInfo: ClassInfo<T> = classInfoCache[targetClass]
    private val valueArguments = mutableMapOf<KParameter, Any?>()
    private val seedArguments = mutableMapOf<KParameter, Seed>()
    private val arguments: Map<KParameter, Any?>
        get() = valueArguments + seedArguments.mapValues { it.value.spawn() }

    override fun spawn(): Any? {
        classInfo.createInstance(arguments)
    }

    override fun createCompositeProperty(propertyName: String, isList: Boolean): Seed {
        val param = classInfo.getConstructorParameter(propertyName)
        val deserializeAs = classInfo.getDeserializeClass(propertyName)
        val seed = createSeedForType(deserializeAs ?: param.type.javaType, isList)
        return seed.apply { seedArguments[param] = this }
    }

    override fun setSimpleProperty(propertyName: String, value: Any?) {
        val param = classInfo.getConstructorParameter(propertyName)
        valueArguments[param] = classInfo.deserializeConstructorArgument(param, value)
    }
}

fun serializeForType(type: Type) : ValueSerializer<out Any?>? =
    when(type) {
        Byte::class.java -> ByteSerializer
        Int::class.java -> IntSerializer
        Boolean::class.java -> BooleanSerializer
        else -> null
    }

object BooleanSerializer : ValueSerializer<Boolean> {
    override fun toJsonValue(value: Boolean) = value

    override fun fromJsonValue(jsonValue: Any?): Boolean {
        if (jsonValue !is Boolean) throw IllegalArgumentException("Boolean expected")
        return jsonValue
    }
}

class ClassInfoCache {
    private val cacheData = mutableMapOf<KClass<*>, ClassInfo<*>>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(cls: KClass<T>): ClassInfo<T> =
        cacheData.getOrPut(cls) { ClassInfo(cls) } as ClassInfo<T>
}

class ClassInfo<T : Any> (cls: KClass<T>) {
    private val constructor = cls.primaryConstructor!!
    private val jsonNameToParam = hashMapOf<String, KParameter>()
    private val paramToSerializer = hashMapOf<KParameter, ValueSerializer<out Any?>>()
    private val jsonNameToDeserializeClass = hashMapOf<String, Class<out Any?>>()

    init {
        constructor.parameters.forEach { cacheDataForParameter(cls, it) }
    }

    fun getConstructorParameter(propertyName: String) : KParameter = jsonNameToParam[propertyName]!!
    fun deserializeConstructorArgument(
        param: KParameter,
        value: Any?
    ): Any? {
        val serializer = paramToSerializer[param]
        if (serializer != null) return serializer.fromJsonValue(value)
        validateArgumentType(param, value)
        return value
    }

    fun createInstance(arguments: Map<KParameter, Any?>): T {
        ensureAllParametersPresent(arguments)
        return constructor.callBy(arguments)
    }

    // ...

    private fun ensureAllParametersPresent(arguments: Map<KParameter, Any?>) {
        for (param in constructor.parameters) {
            if (arguments[param] == null && !param.isOptional && !param.type.isMarkedNullable) {
                throw IllegalStateException("Missing value for parameter ${param.name}")
            }
        }
    }
}
