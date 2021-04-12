package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of a geographical location.
 *
 */
data class Location(
    /**
     * The location identifier.
     */
    var id: UUID,

    /**
     * The location name.
     */
    var name: String,

    /**
     * The location latitude.
     */
    var latitude: Double,

    /**
     * The location longitude.
     */
    var longitude: Double,
)
