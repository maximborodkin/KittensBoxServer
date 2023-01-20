import model.rest.Device
import model.rest.Topic

object Configuration {
    // Server connection configuration
    const val mqttServerAddress = "tcp://127.0.0.1:1883"
    const val mqttServerLogin = ""
    const val mqttServerPassword = ""
    const val clientName = "KittensBoxServer"

    const val serverHost = "0.0.0.0"
    const val serverPort = 8080

    // Devices
    private const val weatherStationId = "1"
    private val weatherStation = Device(
        id = weatherStationId,
        name = "Weather station",
        description = "ESP8266 with weather sensors located in main room"
    )

    private const val lightControlMainRoomId = "2"
    private val lightControlMainRoom = Device(
        id = lightControlMainRoomId,
        name = "Light control in main room",
        description = "ESP8266 module with 12-channel solid state relays module"
    )

    val devices = mapOf(
        weatherStationId to weatherStation,
        lightControlMainRoomId to lightControlMainRoom
    )

    // Topics
    private const val temperatureSensorTopicId = "/sensors/temperature/1"
    private val temperatureSensorTopic = Topic.TemperatureSensorTopic(
        topic = temperatureSensorTopicId,
        device = weatherStation,
        name = "Temperature sensor",
        description = "AHT10 sensor"
    )

    private const val humiditySensorTopicId = "/sensors/humidity/1"
    private val humiditySensorTopic = Topic.HumiditySensorTopic(
        topic = humiditySensorTopicId,
        device = weatherStation,
        name = "Humidity sensor",
        description = "SHT31 sensor"
    )

    private const val atmosphericPressureSensorTopicId = "/sensors/atmospheric_pressure/1"
    private val atmosphericPressureSensorTopic = Topic.AtmosphericPressureSensorTopic(
        topic = atmosphericPressureSensorTopicId,
        device = weatherStation,
        name = "Atmospheric pressure sensor",
        description = "BMP180 sensor. Range from 300hPa to 1100hPa"
    )

    private const val mainRoomLightChannel1TopicId = "/devices/light/main/1"
    private val mainRoomLightChannel1Topic = Topic.RelayControlTopic(
        topic = mainRoomLightChannel1TopicId,
        device = lightControlMainRoom,
        name = "Main room primary light control",
        description = "Solid state relay to control main room primary light"
    )

    private const val mainRoomLightChannel2TopicId = "/devices/light/main/2"
    private val mainRoomLightChannel2Topic = Topic.RelayControlTopic(
        topic = mainRoomLightChannel2TopicId,
        device = lightControlMainRoom,
        name = "Main room secondary light control",
        description = "Solid state relay to control main room secondary light (LED strip)"
    )

    val topics = mapOf(
        temperatureSensorTopicId to temperatureSensorTopic,
        humiditySensorTopicId to humiditySensorTopic,
        atmosphericPressureSensorTopicId to atmosphericPressureSensorTopic,
        mainRoomLightChannel1TopicId to mainRoomLightChannel1Topic,
        mainRoomLightChannel2TopicId to mainRoomLightChannel2Topic
    )
}
