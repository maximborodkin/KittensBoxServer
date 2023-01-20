package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.rest.TopicDataType

object TopicDataTypeSerializer : KSerializer<TopicDataType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TopicDataType", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: TopicDataType) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): TopicDataType {
        val id = decoder.decodeInt()
        return TopicDataType.values().firstOrNull { it.id == id }
            ?: throw IllegalStateException("Can't create TopicDataType from id $id")
    }
}