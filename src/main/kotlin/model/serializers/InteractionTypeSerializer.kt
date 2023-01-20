package model.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.rest.InteractionType

object InteractionTypeSerializer : KSerializer<InteractionType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("InteractionType", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: InteractionType) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): InteractionType {
        val id = decoder.decodeInt()
        return InteractionType.values().firstOrNull { it.id == id }
            ?: throw IllegalStateException("Can't create InteractionType from id $id")
    }
}