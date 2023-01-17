import com.kittensbox.plugins.configureRouting
import com.kittensbox.plugins.configureSerialization
import com.kittensbox.plugins.configureSockets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

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
    configureSockets()
    configureSerialization()
    configureRouting()
}