package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import model.rest.Topic
import model.rest.Topic.*
import model.rest.TopicType

object TopicSerializer : KSerializer<Topic> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Topic", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Topic) {
        when (value) {
            is TemperatureSensorTopic -> encoder.encodeSerializableValue(TemperatureSensorTopic.serializer(), value)
            is HumiditySensorTopic -> encoder.encodeSerializableValue(HumiditySensorTopic.serializer(), value)
            is AtmosphericPressureSensorTopic -> encoder.encodeSerializableValue(AtmosphericPressureSensorTopic.serializer(), value)
            is RelayControlTopic -> encoder.encodeSerializableValue(RelayControlTopic.serializer(), value)
        }
    }

    override fun deserialize(decoder: Decoder): Topic {
        val input = decoder as JsonDecoder
        val jsonObject = input.decodeJsonElement().jsonObject
        val topicTypeId = jsonObject["topicType"]?.jsonPrimitive?.int
            ?: throw IllegalArgumentException("No topic type")
        val topicType = TopicType.values().first { it.id == topicTypeId }
        val json = decoder.json

        return when (topicType) {
            TopicType.TEMPERATURE_SENSOR -> json.decodeFromJsonElement(TemperatureSensorTopic.serializer(), jsonObject)
            TopicType.HUMIDITY_SENSOR -> json.decodeFromJsonElement(HumiditySensorTopic.serializer(), jsonObject)
            TopicType.ATMOSPHERIC_PRESSURE_SENSOR -> json.decodeFromJsonElement(AtmosphericPressureSensorTopic.serializer(), jsonObject)
            TopicType.RELAY_CONTROL -> json.decodeFromJsonElement(RelayControlTopic.serializer(), jsonObject)
        }
    }
}