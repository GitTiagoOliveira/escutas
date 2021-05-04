package pt.ipca.escutas.controllers

import android.content.res.Resources
import pt.ipca.escutas.services.FirebaseAuthService
import pt.ipca.escutas.services.FirebaseDatabaseService
import pt.ipca.escutas.services.FirebaseStorageService

/**
 * Defines the base controller. Further controllers should inherit from this base.
 *
 */
open class BaseController {
    /**
     * The authentication service.
     */
    protected val auth: FirebaseAuthService = FirebaseAuthService()

    /**
     * The database service.
     */
    protected val database: FirebaseDatabaseService = FirebaseDatabaseService()

    /**
     * The storage service.
     */
    protected val storage: FirebaseStorageService = FirebaseStorageService()

    /**
     * The application resources accessor.
     */
    protected val resources: Resources = Resources.getSystem()
}
