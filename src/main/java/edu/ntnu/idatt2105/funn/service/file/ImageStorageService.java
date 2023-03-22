package edu.ntnu.idatt2105.funn.service.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of the service class for the image repository.
 * @author Callum G.
 * @version 1.1 - 22.03.2023
 */
@Service
public class ImageStorageService implements FileStorageService {

  private final Path root = Paths.get("src/main/resources/images");

  @Override
  public void init() throws IOException {
    Files.createDirectory(root);
  }

  @Override
  public void store(MultipartFile file, Long id) throws IOException {
    if (file.getContentType() != "image/jpeg" && file.getContentType() != "image/png") {
      throw new IOException("Only JPG and PNG files are allowed");
    }
    Files.copy(file.getInputStream(), root.resolve(id.toString()));
  }

  @Override
  public Resource loadFile(Long id) throws IOException, MalformedURLException {
    Path file = root.resolve(id.toString());
    Resource resource = new UrlResource(file.toUri());

    if (resource.exists() || resource.isReadable()) {
      return resource;
    } else {
      throw new IOException("Could not read file: " + file.toString());
    }
  }

  @Override
  public void deleteFile(Long id) throws IOException {
    Path file = root.resolve(id.toString());
    Files.delete(file);
  }

  @Override
  public Stream<Path> loadFiles(List<Long> fileIds) throws IOException {
    return Files
      .walk(root, 1)
      .filter(path -> fileIds.contains(Long.parseLong(path.getFileName().toString())));
  }
}
