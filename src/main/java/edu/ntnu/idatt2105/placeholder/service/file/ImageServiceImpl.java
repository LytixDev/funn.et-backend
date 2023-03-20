package edu.ntnu.idatt2105.placeholder.service.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.placeholder.model.file.Image;
import edu.ntnu.idatt2105.placeholder.repository.file.ImageRepository;
import lombok.NonNull;

/**
 * Service for image files.
 * @author Callum G.
 * @version 1.0 - 20.03.2023
 */
@Service
public class ImageServiceImpl implements FileService<Image> {
    
    @Autowired
    private ImageRepository imageRepository;

    /**
     * Checks if a file exists in the database.
     * @param file The file to check.
     * @return True if the file exists, false otherwise.
     * @throws NullPointerException If the file is null.
     */
    @Override
    public boolean fileExists(@NonNull Image file) throws NullPointerException {
        return imageRepository.existsById(file.getId());
    }

    /**
     * Saves a file to the database.
     * @param file The file to save.
     * @return The saved file.
     * @throws DatabaseException If the file could not be saved to the database.
     * @throws NullPointerException If the file is null.
     */
    @Override
    public Image saveFile(@NonNull Image file) throws DatabaseException, NullPointerException {
        if (fileExists(file))
            throw new DatabaseException();
        try {
            return imageRepository.save(file);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    /**
     * Updates a file in the database.
     * @param file The file to update.
     * @return The updated file.
     * @throws FileNotFoundException If the file does not exist in the database.
     * @throws DatabaseException If the file could not be updated in the database.
     * @throws NullPointerException If the file is null.
     */
    @Override
    public Image updateFile(@NonNull Image file) throws FileNotFoundException, DatabaseException, NullPointerException {
        if (!fileExists(file))
            throw new FileNotFoundException();

        try {
            return imageRepository.save(file);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    /**
     * Gets a file from the database.
     * @param id The id of the file to get.
     * @return The file.
     * @throws FileNotFoundException If the file does not exist in the database.
     * @throws DatabaseException If the file could not be retrieved from the database.
     * @throws NullPointerException If the id is null.
     */
    @Override
    public Image getFile(Long id) throws FileNotFoundException, DatabaseException {
        try {
            return imageRepository.findById(id).orElseThrow(FileNotFoundException::new);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    /**
     * Deletes a file from the database.
     * @param file The file to delete.
     * @throws FileNotFoundException If the file does not exist in the database.
     * @throws DatabaseException If the file could not be deleted from the database.
     * @throws NullPointerException If the file is null.
     */
    @Override
    public void deleteFile(@NonNull Image file) throws FileNotFoundException, DatabaseException, NullPointerException {
        if (!fileExists(file))
            throw new FileNotFoundException();

        try {
            imageRepository.delete(file);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    /**
     * Deletes a file from the database.
     * @param id The id of the file to delete.
     * @throws FileNotFoundException If the file does not exist in the database.
     * @throws DatabaseException If the file could not be deleted from the database.
     */
    @Override
    public void deleteFile(Long id) throws FileNotFoundException, DatabaseException {
        if (!imageRepository.existsById(id))
            throw new FileNotFoundException();

        try {
            imageRepository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    /**
     * Gets all files from the database.
     * @return A list of all files.
     */
    @Override
    public List<Image> getAllFiles() {
        return imageRepository.findAll();
    }
}
