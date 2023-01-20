import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import plugins.MqttClient
import plugins.configureRestServer
import plugins.configureWebsocketServer

private val mqttClient = MqttClient()

fun main() {
    embeddedServer(
        factory = Netty,
        host = Configuration.serverHost,
        port = Configuration.serverPort,
        module = Application::module
    )
        .start(wait = true)
}

private fun Application.module() {
    configureWebsocketServer(mqttClient)
    configureRestServer()
}