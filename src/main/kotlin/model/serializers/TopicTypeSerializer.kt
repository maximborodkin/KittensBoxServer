package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.rest.TopicType

object TopicTypeSerializer : KSerializer<TopicType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TopicType", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: TopicType) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): TopicType {
        val id = decoder.decodeInt()
        return TopicType.values().firstOrNull { it.id == id }
            ?: throw IllegalStateException("Can't create TopicType from id $id")
    }
}