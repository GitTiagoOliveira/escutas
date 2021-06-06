package pt.ipca.escutas.services.callbacks

interface GenericCallback {

    /**
     * Returns [value] of  required object.
     *
     * @param value
     */
    fun onCallback(value: Any?)
}
