package pt.ipca.escutas.services.callbacks

interface AuthCallback {

    /**
     * Returns callback where function was invoked.
     */
    fun onCallback()

    /**
     * Returns [error] related to operation.
     *
     * @param error
     */
    fun onCallbackError(error: String)
}
