package helpers

import kotlinx.serialization.json.Json
import model.rest.Device
import model.rest.Topic
import model.websocket.WebsocketMessage

object JsonWrapper {
    val json = Json

    fun encodeToString(value: Any): String {
        return when (value) {
            is Topic -> json.encodeToString(Topic.serializer(), value)
            is Device -> json.encodeToString(Device.serializer(), value)
            is WebsocketMessage -> json.encodeToString(WebsocketMessage.serializer(), value)
            else -> throw IllegalArgumentException("No serializer for type ${value::class.simpleName}")
        }
    }

    inline fun <reified T>decodeFromString(value: String) : T {
        return json.decodeFromString(WebsocketMessage.serializer(), value) as T
//        return when {
//            Topic is T -> json.decodeFromString(Topic.serializer(), value) as T
//            Device is T -> json.decodeFromString(Device.serializer(), value) as T
//            WebsocketMessage is T -> json.decodeFromString(WebsocketMessage.serializer(), value) as T
//            else -> throw IllegalArgumentException("No deserializer for type ${T::class.simpleName}")
//        }
    }
}