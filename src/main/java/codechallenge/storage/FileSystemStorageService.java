package codechallenge.storage;

import codechallenge.validation.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Concrete implementation of a "to" file system storage service
 */
@Service
@EnableAutoConfiguration
public class FileSystemStorageService implements StorageService {

    private Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private String rootLocation;

    public FileSystemStorageService(@Value("${rootLocation}") String rootLocation) {
        this.rootLocation = rootLocation;
    }

    @Override
    public void upload(Validation validation) throws StorageException {
        try {
            new ObjectMapper().writeValue(new File(String.format("%s%s.json", rootLocation, validation.getId())), validation);
        } catch (IOException e) {
            logger.error(String.format("Failed to store file validation: %s, %s", validation.getId(), e.getMessage()));
            throw new StorageException("Failed to store file ", e);
        }
    }

    @Override
    public String get(String filename) throws StorageException {
        try {
            Path file = Paths.get(rootLocation).resolve(String.format("%s.json", filename));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return inputStreamToString(resource.getInputStream());
            } else {
                logger.error(String.format("Could not read file: %s.json", filename));
                throw new StorageException("Could not read file: " + filename+".json");
            }
        }
        catch (IOException e) {
            logger.error(String.format("Could not read file: %s.json", filename));
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    /**
     * Extracts a string from a inputstream
     * @param inputStream the inputstream
     * @return a string
     * @throws StorageException
     */
    private String inputStreamToString(InputStream inputStream) throws StorageException {
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString();
        } catch (IOException e) {
            logger.error(String.format("Could not initialize storage: %z", e.getMessage()));
            throw new StorageException("Could not initialize storage", e);
        }
    }
}