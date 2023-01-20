package model.rest

import kotlinx.serialization.Serializable
import model.serializers.TopicTypeSerializer

@Serializable(with = TopicTypeSerializer::class)
enum class TopicType(val id: Int) {
    TEMPERATURE_SENSOR(id = 1),
    HUMIDITY_SENSOR(id = 2),
    ATMOSPHERIC_PRESSURE_SENSOR(id = 3),

    RELAY_CONTROL(id = 4);
}