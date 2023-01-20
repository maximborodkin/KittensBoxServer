package model.rest

import kotlinx.serialization.Serializable

/**
 * Abstract class that represents a topic of an IoT device
 *
 * @param topic unique topic address
 * @param device represents physical device
 * @param type enumeration. Describes set of features of topic.
 * @param interactionType how can client interact with this topic. Read or write
 * @param dataType describes witch type of data this topic used
 * @param description physical module description and it purpose
 */
@Serializable
sealed class Topic(
    val device: Device,
    val topic: String,
    val name: String,
    val description: String?,
    val type: TopicType,
    val interactionType: InteractionType,
    val dataType: TopicDataType
) {
    class TemperatureSensorTopic(
        device: Device,
        topic: String,
        name: String,
        description: String?
    ) : Topic(
        device = device,
        topic = topic,
        name = name,
        description = description,
        type = TopicType.TEMPERATURE_SENSOR,
        interactionType = InteractionType.READ,
        dataType = TopicDataType.DOUBLE
    )

    class HumiditySensorTopic(
        device: Device,
        topic: String,
        name: String,
        description: String?
    ) : Topic(
        device = device,
        topic = topic,
        name = name,
        description = description,
        type = TopicType.HUMIDITY_SENSOR,
        interactionType = InteractionType.READ,
        dataType = TopicDataType.DOUBLE
    )

    class AtmosphericPressureSensorTopic(
        device: Device,
        topic: String,
        name: String,
        description: String?
    ) : Topic(
        device = device,
        topic = topic,
        name = name,
        description = description,
        type = TopicType.ATMOSPHERIC_PRESSURE_SENSOR,
        interactionType = InteractionType.READ,
        dataType = TopicDataType.DOUBLE
    )

    class RelayControlTopic(
        device: Device,
        topic: String,
        name: String,
        description: String?
    ) : Topic(
        device = device,
        topic = topic,
        name = name,
        description = description,
        type = TopicType.RELAY_CONTROL,
        interactionType = InteractionType.WRITE,
        dataType = TopicDataType.BOOLEAN
    )
}