import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import plugins.configureMqttClient

private const val HOST = "0.0.0.0"
private const val PORT = 8080

fun main() {
    embeddedServer(
        factory = Netty,
        port = PORT,
        host = HOST,
        module = Application::module
    )
        .start(wait = true)
}

private fun Application.module() {
//    configureSockets()
    configureMqttClient()
}