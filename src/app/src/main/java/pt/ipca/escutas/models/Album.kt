package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of an album.
 *
 */
data class Album(
    /**
     * The album identifier.
     */
    var id: UUID,

    /**
     * The album name.
     */
    var name: String,

    /**
     * The main Image from Album.
     */
    var image: String
)
