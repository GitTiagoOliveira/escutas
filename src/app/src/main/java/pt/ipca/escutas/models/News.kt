package pt.ipca.escutas.models

import java.util.UUID

/**
 * Defines the model of a news.
 *
 */
data class News(
    /**
     * The news identifier.
     */
    var id: UUID,

    /**
     * The news title.
     */
    var title: String,

    /**
     * The news body.
     */
    var body: String,

    /**
     * The news details.
     */
    var details: String,

    /**
     * The news image path.
     */
    var image: String,
)
