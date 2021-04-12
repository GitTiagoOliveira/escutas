package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of a scout group.
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
     * The group [Location].
     */
    var location: Location,

    /**
     * The group [Image].
     */
    var image: Image,
)
