package edu.ntnu.idatt2105.funn.service.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;

/**
 * Interface for the service class for the image repository.
 * @author Callum G.
 * @version 1.2 - 26.03.2023
 */
public interface FileStorageService {
  void init() throws IOException;

  void store(MultipartFile file, Long id) throws IOException;

  Resource loadFile(Long id) throws IOException, MalformedURLException, FileNotFoundException;

  void deleteFile(Long id) throws IOException;

  Stream<Path> loadFiles(List<Long> fileIds) throws IOException;
}
