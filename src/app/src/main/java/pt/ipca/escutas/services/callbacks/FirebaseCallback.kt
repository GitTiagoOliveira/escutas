package pt.ipca.escutas.services.callbacks

interface FirebaseCallback {
    fun onCallback(list: HashMap<String, Any>)
}
