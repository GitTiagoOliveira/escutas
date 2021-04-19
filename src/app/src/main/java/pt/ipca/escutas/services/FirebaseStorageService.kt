package pt.ipca.escutas.services

import android.content.res.Resources
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import pt.ipca.escutas.R
import pt.ipca.escutas.services.contracts.IStorageService
import pt.ipca.escutas.services.exceptions.StorageException
import java.io.FileInputStream

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
     * The application resources accessor.
     */
    private val resources: Resources = Resources.getSystem()

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
    override fun createFile(filePath: String, fileStream: FileInputStream) {
        this.storage
            .getReference(filePath)
            .putStream(fileStream)
            .addOnFailureListener {
                throw StorageException(resources.getString(R.string.exception_storage_create))
            }
    }

    /**
     * Reads a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @return The file byte sequence.
     */
    override fun readFile(filePath: String): Task<ByteArray> {
        return this.storage
            .getReference(filePath)
            .getBytes(maxBytes)
            .addOnFailureListener {
                throw StorageException(resources.getString(R.string.exception_storage_read))
            }
    }

    /**
     * Reads a file storage reference in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @return The file reference in the storage service.
     */
    fun readReference(filePath: String): StorageReference {
        return this.storage
            .getReference(filePath)
    }

    /**
     * Updates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The file path in the storage service.
     * @param fileStream The file input stream.
     */
    override fun updateFile(filePath: String, fileStream: FileInputStream) {
        try {
            this.deleteFile(filePath)
            this.createFile(filePath, fileStream)
        } catch (e: StorageException) {
            throw StorageException(resources.getString(R.string.exception_storage_update))
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
                throw StorageException(resources.getString(R.string.exception_storage_delete))
            }
    }
}
