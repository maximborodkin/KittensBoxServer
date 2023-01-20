package model.rest

import kotlinx.serialization.Serializable
import model.serializers.TopicDataTypeSerializer

@Serializable(with = TopicDataTypeSerializer::class)
enum class TopicDataType(val id: Int) {
    INT(id = 1),
    DOUBLE(id = 2),
    BOOLEAN(id = 3),
    STRING(id = 4)
}
