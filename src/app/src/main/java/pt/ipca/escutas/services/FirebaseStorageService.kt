package pt.ipca.escutas.services

import com.google.firebase.storage.StorageReference
import pt.ipca.escutas.services.contracts.IStorageService
import java.io.FileInputStream

/**
 * Defines a Firebase implementation of an [IStorageService].
 *
 */
class FirebaseStorageService : IStorageService {
    /**
     * Creates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The destination file path in the storage service.
     * @param fileStream The file input stream.
     */
    override fun createFile(filePath: String, fileStream: FileInputStream) {
        TODO("Not yet implemented")
    }

    /**
     * Reads a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @return The file byte sequence.
     */
    override fun readFile(filePath: String): Array<Byte> {
        TODO("Not yet implemented")
    }

    /**
     * Reads a file storage reference in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     * @return The file reference in the storage service.
     */
    fun readReference(filePath: String): StorageReference {
        TODO("Not yet implemented")
    }

    /**
     * Updates a file in the storage service through the specified [filePath] and [fileStream].
     *
     * @param filePath The file path in the storage service.
     * @param fileStream The file input stream.
     */
    override fun updateFile(filePath: String, fileStream: FileInputStream) {
        TODO("Not yet implemented")
    }

    /**
     * Deletes a file in the storage service through the specified [filePath].
     *
     * @param filePath The file path in the storage service.
     */
    override fun deleteFile(filePath: String) {
        TODO("Not yet implemented")
    }
}
