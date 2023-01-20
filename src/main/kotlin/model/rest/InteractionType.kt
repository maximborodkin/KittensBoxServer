package model.rest

import kotlinx.serialization.Serializable
import model.serializers.InteractionTypeSerializer

@Serializable(with = InteractionTypeSerializer::class)
enum class InteractionType(val id: Int) {
    READ(id = 1),
    WRITE(id = 2)
}