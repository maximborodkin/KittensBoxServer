package plugins

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage

fun configureMqttClient() {
    val mqttServerAddress = "tcp://127.0.0.1:1883"
    val client = MqttClient(mqttServerAddress, "KittensBoxServer")
    client.setCallback(object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            println("connectionLost ${cause?.message}")
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            println("messageArrived topic: $topic, message: ${message?.payload?.count()}")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            println("deliveryComplete $token")
        }
    })
    client.connect()
    client.subscribe("/test") { topic, message -> println("subscribe /test: $topic ${String(message.payload)}") }
    client.subscribe("/control/#") { topic, message -> println("subscribe /control: $topic ${String(message.payload)}") }
    client.subscribe("/control/c1/#") { topic, message -> println("subscribe /control/c1: $topic ${String(message.payload)}") }
    client.publish("test", MqttMessage("hello from server".toByteArray()))
}