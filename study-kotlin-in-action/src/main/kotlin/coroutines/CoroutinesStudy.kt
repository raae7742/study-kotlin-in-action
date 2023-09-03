package coroutines

import kotlin.coroutines.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)
fun log(msg: String) = println("${now()}:${Thread.currentThread()}: $msg")
fun launchInGlobalScope() {
    GlobalScope.launch {
        log("coroutine started.")
    }
}

fun main() {
    log("main() started.")
    launchInGlobalScope()
    log("launchInGlobalScope() executed")
    Thread.sleep(5000L)
    log("main() terminated")
}

fun runBlockingExample() {
    runBlocking {
        launch {
            log("GlobalScope.launch started.")
        }
    }
}