package model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class TopicReply(
    val subscriptionId: Long,
    val topic: String,
    val reply: String
) : PayloadMessage