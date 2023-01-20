package model.rest

import kotlinx.serialization.Serializable
import model.serializers.TopicSerializer

/**
 * Abstract class that represents a topic of an IoT device
 *
 * @property topic unique topic address
 * @property device represents physical device
 * @property topicType enumeration. Describes set of features of topic.
 * @property interactionType how can client interact with this topic. Read or write
 * @property dataType describes witch type of data this topic used
 * @property description physical module description and it purpose
 */
@Serializable(with = TopicSerializer::class)
sealed class Topic {
    abstract val device: Device
    abstract val topic: String
    abstract val name: String
    abstract val description: String?
    abstract val topicType: TopicType
    abstract val interactionType: InteractionType
    abstract val dataType: TopicDataType

    @Serializable
    data class TemperatureSensorTopic(
        override val device: Device,
        override val topic: String,
        override val name: String,
        override val description: String?
    ) : Topic() {
        override val topicType = TopicType.TEMPERATURE_SENSOR
        override val interactionType = InteractionType.READ
        override val dataType = TopicDataType.DOUBLE
    }

    @Serializable
    class HumiditySensorTopic(
        override val device: Device,
        override val topic: String,
        override val name: String,
        override val description: String?
    ) : Topic() {
        override val topicType = TopicType.HUMIDITY_SENSOR
        override val interactionType = InteractionType.READ
        override val dataType = TopicDataType.DOUBLE
    }

    @Serializable
    class AtmosphericPressureSensorTopic(
        override val device: Device,
        override val topic: String,
        override val name: String,
        override val description: String?
    ) : Topic() {
        override val topicType = TopicType.ATMOSPHERIC_PRESSURE_SENSOR
        override val interactionType = InteractionType.READ
        override val dataType = TopicDataType.DOUBLE
    }

    @Serializable
    class RelayControlTopic(
        override val device: Device,
        override val topic: String,
        override val name: String,
        override val description: String?
    ) : Topic() {
        override val topicType = TopicType.RELAY_CONTROL
        override val interactionType = InteractionType.WRITE
        override val dataType = TopicDataType.BOOLEAN
    }
}