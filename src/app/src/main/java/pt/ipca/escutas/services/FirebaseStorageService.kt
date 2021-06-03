package pt.ipca.escutas.services

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.services.contracts.IStorageService
import pt.ipca.escutas.services.exceptions.DatabaseException
import pt.ipca.escutas.services.exceptions.StorageException
import java.io.InputStream

/**
 * Defines a Firebase implementation of an [IStorageService].
 *
 */
class FirebaseStorageService : IStorageService {
    /**
     * The firebase storage service.
     */
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    /**
     * The maximum size, in bytes, of an image that can be read.
     */
    private val maxBytes: Long = 32 * 1024 * 1024

    /**
     * Creates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The destination file path in the storage service.
     * @param fileStream The file input stream.
     */
    override fun createFile(filePath: String, fileStream: InputStream, callback: StorageCallback) {

        this.storage
            .getReference(filePath)
            .putStream(fileStream)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onCallback(null)
                } else {
                    Log.w(ContentValues.TAG, Strings.MSG_FAIL_STORAGE_CREATE, task.exception)
                    throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_STORAGE_CREATE)
                }
            }
    }

    /**
     * Reads a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @return The file byte sequence.
     */
    override fun readFile(filePath: String, callback: StorageCallback): Task<ByteArray> {
        return this.storage
            .getReference(filePath)
            .getBytes(maxBytes).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val options = BitmapFactory.Options()
                    options.inMutable = true
                    val image = BitmapFactory.decodeByteArray(task.result, 0, task.result.size, options)
                    callback.onCallback(image)
                } else {
                    Log.w(ContentValues.TAG, Strings.MSG_FAIL_STORAGE_READ, task.exception)
                    throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_STORAGE_READ)
                }
            }
    }

    /**
     * Updates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The file path in the storage service.
     * @param fileStream The file input stream.
     */
    override fun updateFile(filePath: String, fileStream: InputStream) {
        try {
            this.deleteFile(filePath)
            this.createFile(
                filePath, fileStream,
                object : StorageCallback {
                    override fun onCallback(image: Bitmap?) {
                        TODO("Not yet implemented")
                    }
                }
            )
        } catch (e: StorageException) {
            throw StorageException(Strings.MSG_FAIL_STORAGE_UPDATE)
        }
    }

    /**
     * Deletes a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     */
    override fun deleteFile(filePath: String) {
        this.storage
            .getReference(filePath)
            .delete()
            .addOnFailureListener {
                throw StorageException(Strings.MSG_FAIL_STORAGE_DELETE)
            }
    }
}
