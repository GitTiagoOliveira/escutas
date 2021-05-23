package pt.ipca.escutas.services.callbacks

import android.graphics.Bitmap

interface StorageCallback {

    /**
     * Returns [image] in Bitmap format.
     *
     * @param image
     */
    fun onCallback(image: Bitmap)
}