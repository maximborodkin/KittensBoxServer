package model

/**
 * The Quality of Service (QoS) level is an agreement between the sender of a message and
 * the receiver of a message that defines the guarantee of delivery for a specific message.
 * [AT_MOST_ONCE] means there is no guarantee of delivery. Fastest speed of delivery
 * [AT_LEAST_ONCE] guarantees that a message is delivered at least one time to the receiver.
 * It is possible for a message to be sent or delivered multiple times
 * [EXACTLY_ONCE] guarantees that each message is received only once by the intended recipients. Slowest delivery
 */
enum class QOS(val value: Int) {
    AT_MOST_ONCE(value = 0),
    AT_LEAST_ONCE(value = 1),
    EXACTLY_ONCE(value = 2)
}