package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of a group.
 *
 */
data class Group(
    /**
     * The group identifier.
     */
    var id: UUID,

    /**
     * The group name.
     */
    var name: String,

    /**
     * The group description.
     */
    var description: String,

    /**
     * The group latitude.
     */
    var latitude: Double,

    /**
     * The group longitude.
     */
    var longitude: Double,
)
