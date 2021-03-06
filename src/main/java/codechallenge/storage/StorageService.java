package codechallenge.storage;

import codechallenge.validation.Validation;

/**
 * Interface for a storage service
 */
public interface StorageService {

    /**
     * Retrives a file from the storage and returns its contents as a string
     * @param filename the name of the file to load
     * @return a string representing the contents of the stored file
     * @throws StorageException
     */
    String get(String filename) throws StorageException;

    /**
     * Manages the  upload of a file, its contents validation and the storage of the overall data together with the validation results
     * @param validation the results of the validation procedure
     * @throws StorageException
     */
    void upload(Validation validation) throws StorageException;
}