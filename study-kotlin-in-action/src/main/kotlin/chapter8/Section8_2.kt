package chapter8

import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.locks.Lock

// 인라인 함수
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    }
    finally {
        lock.unlock()
    }
}


/**
 * filter -> map: 중간 리스트로 인한 성능 저하 발생
 * asSequence()로 성능 개선이 가능한 것은 컬렉션의 크기가 큰 경우뿐이다.
 * asSequence()는 인라이닝이 불가능하기 때문에 컬렉션 크기가 작다면 일반 컬렉션 함수를 사용하는 것이 오히려 유리하다.
 */

fun readFirstLineFromFile(path: String): String {
    BufferedReader(FileReader(path)).use {br ->
        return br.readLine()
    }
}