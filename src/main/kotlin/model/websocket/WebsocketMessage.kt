package model.websocket

import kotlinx.serialization.Serializable
import model.serializers.WebsocketMessageSerializer

@Serializable(with = WebsocketMessageSerializer::class)
data class WebsocketMessage(
    val messageType: WebsocketMessageType,
    val payload: PayloadMessage
)