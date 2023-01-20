package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.websocket.SubscribeTopic
import model.websocket.TopicReply
import model.websocket.WebsocketMessage
import model.websocket.WebsocketMessageType

object WebsocketMessageSerializer : KSerializer<WebsocketMessage> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("WebsocketMessage") {
        element("messageType", PrimitiveSerialDescriptor("messageType", PrimitiveKind.INT))
        element("payload", PrimitiveSerialDescriptor("payload", PrimitiveKind.STRING))
    }

    override fun serialize(encoder: Encoder, value: WebsocketMessage) {
        val compositeOutput = encoder.beginStructure(descriptor)
        compositeOutput.encodeIntElement(descriptor, 0, value.messageType.id)
        encodePayloadMessage(value, 1, compositeOutput)
        compositeOutput.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): WebsocketMessage {
        val jsonDecoder = decoder as JsonDecoder
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val messageTypeId = jsonObject["messageType"]?.jsonPrimitive?.int
            ?: throw IllegalArgumentException("messageTypeId is null")
        val messageType = WebsocketMessageType.values().first { it.id == messageTypeId }
        val deserializer = getDeserializer(messageType)
        val payloadObject = jsonObject["payload"]?.jsonObject ?: throw IllegalArgumentException("no payload")
        val payload = jsonDecoder.json.decodeFromJsonElement(deserializer, payloadObject)
        return WebsocketMessage(
            messageType = messageType,
            payload = payload
        )
    }

    @Suppress("SameParameterValue")
    private fun encodePayloadMessage(websocketMessage: WebsocketMessage, index: Int, compositeEncoder: CompositeEncoder) {
        when (websocketMessage.messageType) {
            WebsocketMessageType.SUBSCRIBE_TOPIC -> compositeEncoder.encodeSerializableElement(
                descriptor = descriptor,
                index = index,
                serializer = SubscribeTopic.serializer(),
                value = websocketMessage.payload as SubscribeTopic
            )

            WebsocketMessageType.TOPIC_REPLY -> compositeEncoder.encodeSerializableElement(
                descriptor = descriptor,
                index = index,
                serializer = TopicReply.serializer(),
                value = websocketMessage.payload as TopicReply
            )
        }
    }

    private fun getDeserializer(messageType: WebsocketMessageType) = when (messageType) {
        WebsocketMessageType.SUBSCRIBE_TOPIC -> SubscribeTopic.serializer()
        WebsocketMessageType.TOPIC_REPLY -> TopicReply.serializer()
    }
}