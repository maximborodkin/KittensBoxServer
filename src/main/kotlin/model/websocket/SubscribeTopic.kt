package model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeTopic(
    val subscriptionId: Long,
    val topic: String
) : PayloadMessage