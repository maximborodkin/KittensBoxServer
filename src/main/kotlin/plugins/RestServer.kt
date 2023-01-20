package plugins

import Configuration
import helpers.JsonWrapper
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.builtins.ListSerializer
import model.rest.Device
import model.rest.Topic

fun Application.configureRestServer() {
    routing {
        get("/devices") {
            val devices = getDevices()
            val serializedDevices = JsonWrapper.json.encodeToString(ListSerializer(Device.serializer()), devices)
            call.respond(serializedDevices)
        }
        get("/topics") {
            val topics = getTopics()
            val serializedTopics = JsonWrapper.json.encodeToString(ListSerializer(Topic.serializer()), topics)
            call.respondText(serializedTopics)
        }
    }
}

private fun getTopics(): List<Topic> {
    return Configuration.topics.values.sortedWith(compareBy ( { it.device.name }, { it.name } ))
}

private fun getDevices(): List<Device> {
    return Configuration.devices.values.sortedBy { device -> device.name }
}