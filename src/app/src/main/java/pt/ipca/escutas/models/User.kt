package pt.ipca.escutas.models

import java.util.Date
import java.util.UUID

/**
 * Defines the model of an application user.
 *
 */
data class User(
    /**
     * The user identifier.
     */
    var id: UUID,

    /**
     * The user photo.
     */
    var photo: String,

    /**
     * The user email.
     */
    var email: String,

    /**
     * The user name.
     */
    var name: String,

    /**
     * The user birthday.
     */
    var birthday: Date?,

    /**
     * The [Group] that the user belongs to.
     */
    var groupName: String,
)
