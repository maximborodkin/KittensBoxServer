package model.websocket

import kotlinx.serialization.Serializable
import model.serializers.WebsocketMessageTypeSerializer

@Serializable(with = WebsocketMessageTypeSerializer::class)
enum class WebsocketMessageType(val id: Int) {
    SUBSCRIBE_TOPIC(id = 1),
    TOPIC_REPLY(id = 2)
}
