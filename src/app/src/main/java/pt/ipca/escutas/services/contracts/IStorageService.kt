package pt.ipca.escutas.services.contracts

import com.google.android.gms.tasks.Task
import pt.ipca.escutas.services.callbacks.StorageCallback
import java.io.InputStream

/**
 * Defines the contract of a storage service.
 *
 */
interface IStorageService {
    /**
     * Creates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The destination file path in the storage service.
     * @param fileStream The file input stream.
     */
    fun createFile(filePath: String, fileStream: InputStream, callback: StorageCallback)

    /**
     * Reads a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @param callback The callback function to handle async request.
     * @return The file byte sequence.
     */
    fun readFile(filePath: String, callback: StorageCallback): Task<ByteArray>

    /**
     * Updates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The file path in the storage service.
     * @param fileStream The file input stream.
     */
    fun updateFile(filePath: String, fileStream: InputStream)

    /**
     * Deletes a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     */
    fun deleteFile(filePath: String)
}
