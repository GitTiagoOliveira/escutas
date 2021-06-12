package pt.ipca.escutas.models

import java.util.Date
import java.util.UUID

/**
 * Defines the model of an event.
 *
 */
data class Event(
    /**
     * The event identifier.
     */
    var id: UUID,

    /**
     * The event name.
     */
    var name: String,

    /**
     * The event description.
     */
    var description: String,

    /**
     * The event start date.
     */
    var startDate: Date,

    /**
     * The event end date.
     */
    var endDate: Date,

    /**
     * The event attachment [Image].
     */
    var attachment: String,

    /**
     * Whether the event should be shared or not.
     */
    var Shared: Boolean,
)
