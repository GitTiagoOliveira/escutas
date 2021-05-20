package pt.ipca.escutas.services.callbacks

interface FirebaseDBCallback {
    fun onCallback(list: HashMap<String, Any>)
}
