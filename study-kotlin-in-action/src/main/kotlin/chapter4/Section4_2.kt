package chapter4


/** 주 생성자와 초기화 블록 */
class User1 constructor(_nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

class User2(_nickname: String) {
    val nickname = _nickname
}

class User3(val nickname: String)

open class User4(val nickname: String)
class TwitterUser(nickname: String) : User4(nickname)

/** 주 생성자 비공개 설정 */
class Secretive private constructor()

/** 부 생성자 */
//class MyButton : View {
//    constructor(ctx: Context) : super(ctx) {}
//
//    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr) {}
//    constructor(ctx: Context): this(ctx, MY_STYLE) {}
//}

/** 인터페이스에 선언된 프로퍼티 구현 */
interface User {
    val nickname: String
}

class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@')
}

class FacebookUser(private val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)

    private fun getFacebookName(accountId: Int): String { return "" }
}

class User5(val name: String) {
    var address: String = "unspecified"
        set (value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
            field = value
        }
}

class LengthCounter {
    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length
    }
}