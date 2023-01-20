package model.rest

import kotlinx.serialization.Serializable

/**
 * Class that represents physical IoT device
 *
 * @param id unique device identifier
 * @param name displayed device name
 * @param description displayed device description for better understanding device location and purpose
 */
@Serializable
data class Device(
    val id: String,
    val name: String,
    val description: String?
)