package pt.ipca.escutas.services.callbacks

interface FirebaseDBCallback {

    /**
     * Returns [list] of objects.
     *
     * @param list
     */
    fun onCallback(list: HashMap<String, Any>)
}
