package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.websocket.WebsocketMessageType

object WebsocketMessageTypeSerializer : KSerializer<WebsocketMessageType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("WebsocketMessageType", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: WebsocketMessageType) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): WebsocketMessageType {
        val id = decoder.decodeInt()
        return WebsocketMessageType.values().firstOrNull { it.id == id }
            ?: throw IllegalStateException("Can't create WebsocketMessageType from id $id")
    }
}