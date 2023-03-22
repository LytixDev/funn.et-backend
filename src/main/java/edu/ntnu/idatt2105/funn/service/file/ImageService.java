package edu.ntnu.idatt2105.funn.service.file;

import edu.ntnu.idatt2105.funn.model.file.Image;
import org.springframework.stereotype.Service;

/**
 * Interface for the service class for the image repository.
 * @author Callum G.
 * @version 1.1 - 22.03.2023
 */
@Service
public interface ImageService extends FileService<Image> {}
