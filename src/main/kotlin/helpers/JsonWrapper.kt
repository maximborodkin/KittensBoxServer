package helpers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import model.rest.Device
import model.rest.Topic
import model.websocket.WebsocketMessage

object JsonWrapper {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun encodeToString(value: Any): String {
        return when (value) {
            is Topic -> json.encodeToString(Topic.serializer(), value)
            is Device -> json.encodeToString(Device.serializer(), value)
            is WebsocketMessage -> json.encodeToString(WebsocketMessage.serializer(), value)
            else -> throw IllegalArgumentException("No serializer for type ${value::class.simpleName}")
        }
    }

    inline fun <reified T>encodeToString(value: List<T>): String {
        val serializer = getSerializer<T>()
        return json.encodeToString(ListSerializer(elementSerializer = serializer), value)
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T>getSerializer(): KSerializer<T> {
        return when(T::class) {
            Topic::class -> Topic.serializer() as KSerializer<T>
            Device::class -> Device.serializer() as KSerializer<T>
            WebsocketMessage::class -> WebsocketMessage.serializer() as KSerializer<T>
            else -> throw IllegalArgumentException("No serializer for type ${T::class.simpleName}")
        }
    }

     inline fun <reified T>decodeFromString(value: String) : T {
         val serializer = getSerializer<T>()
         return json.decodeFromString(serializer, value)
    }
}