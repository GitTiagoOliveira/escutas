package pt.ipca.escutas.models

import java.util.*

/**
 * Defines the model of an image.
 *
 */
data class Image(
    /**
     * The image identifier.
     */
    var id: UUID,

    /**
     * The image unique resource identifier.
     */
    var uri: String
)
