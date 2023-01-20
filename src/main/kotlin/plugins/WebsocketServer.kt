package plugins

import Configuration
import helpers.JsonWrapper
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import model.websocket.SubscribeTopic
import model.websocket.TopicReply
import model.websocket.WebsocketMessage
import model.websocket.WebsocketMessageType

private val scope = CoroutineScope(Dispatchers.Main)

fun Application.configureWebsocketServer(mqttClient: MqttClient) {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }


    routing {
        webSocket("/") {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()

                    val message = JsonWrapper.decodeFromString<WebsocketMessage>(text)
                    print(message)
                    when (message.messageType) {
                        WebsocketMessageType.SUBSCRIBE_TOPIC -> subscribeTopic(
                            mqttClient = mqttClient,
                            subscribeMessage = message.payload as SubscribeTopic,
                            session = this
                        )

                        WebsocketMessageType.TOPIC_REPLY -> { }
                    }

//                    outgoing.send(Frame.Text("YOU SAID: $text"))
//                    if (text.equals("bye", ignoreCase = true)) {
//                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//                    }
                }
            }
        }
    }
}

private suspend fun subscribeTopic(mqttClient: MqttClient, subscribeMessage: SubscribeTopic, session: DefaultWebSocketSession) {
    val topic = Configuration.topics[subscribeMessage.topic] ?: return
    mqttClient.subscribe(topic) { message ->
        val reply = TopicReply(
            subscriptionId = subscribeMessage.subscriptionId,
            topic = topic.topic,
            reply = message
        )
        val websocketMessage = WebsocketMessage(messageType = WebsocketMessageType.TOPIC_REPLY, payload = reply)
        val serializedReply = JsonWrapper.encodeToString(websocketMessage)

        runBlocking {
            session.outgoing.send(Frame.Text(serializedReply))
        }
    }
    session.outgoing.send(Frame.Text("Subscribed to ${topic.topic}"))
}