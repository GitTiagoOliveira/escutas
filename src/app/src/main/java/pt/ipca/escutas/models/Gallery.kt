package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of a gallery.
 *
 */
data class Gallery(
    /**
     * The gallery identifier.
     */
    var id: UUID,

    /**
     * The gallery name.
     */
    var name: String,

    /**
     * The gallery [Album] collection.
     */
    var albums: Collection<Album>,
)
