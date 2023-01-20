package plugins

import Configuration
import model.QOS
import model.rest.Topic
import org.eclipse.paho.client.mqttv3.*

class MqttClient {
    private val client = MqttClient(Configuration.mqttServerAddress, Configuration.clientName)

    init {
        connect()
    }

    internal fun subscribe(topic: Topic, callback: (String) -> Unit) {
        client.subscribe(topic.topic) { receivedTopic, message ->
            if (receivedTopic == topic.topic) {
                callback(String(message.payload))
            }
        }
    }

    internal fun publish(topic: Topic, message: String, qos: QOS = QOS.EXACTLY_ONCE) {
        val mqttMessage = MqttMessage(message.toByteArray(charset = Charsets.UTF_8))
        mqttMessage.qos = qos.value
        client.publish(topic.topic, mqttMessage)
    }

    private fun connect() {
        val connectionOptions = MqttConnectOptions()
        connectionOptions.userName = Configuration.mqttServerLogin
        connectionOptions.password = Configuration.mqttServerPassword.toCharArray()
        client.connect(connectionOptions)
    }

    private fun disconnect() {
        client.disconnect(2000)
    }
}