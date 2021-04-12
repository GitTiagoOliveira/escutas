package pt.ipca.escutas.models

import android.net.Uri
import java.util.UUID

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
    var uri: Uri?,
)
