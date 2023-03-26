package edu.ntnu.idatt2105.funn.service.file;

import java.io.File;
import java.io.FilenameFilter;
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

import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;

/**
 * Implementation of the service class for the image repository.
 * @author Callum G., Carl G.
 * @version 1.2 - 26.03.2023
 */
@Service
public class ImageStorageService implements FileStorageService {

  private final Path root = Paths.get("src/main/resources/images");

  @Override
  public void init() throws IOException {
    Files.createDirectories(root);
  }

  @Override
  public void store(MultipartFile file, Long id) throws IOException {
    String contentType = file.getContentType();
    if (
      !(contentType.equals("image/jpeg") || contentType.equals("image/png"))
    ) {
      throw new IOException("Only JPG and PNG files are allowed. Got " + file.getContentType());
    }
    Files.copy(
      file.getInputStream(),
      root.resolve(
        id.toString() +
        "." +
        contentType.substring(contentType.lastIndexOf("/") + 1)
      )
    );
  }

  /**
   * Loads a file from the root directory
   * @param id The id and name of the file to be loaded
   * @return The file as a resource
   * @throws FileNotFoundException If the file could not be found
   * @throws IOException If the file is not readable
   * @throws MalformedURLException If the file path could not be correctly parsed
   */
  @Override
  public Resource loadFile(Long id) throws IOException, MalformedURLException, FileNotFoundException {
    File[] foundFiles = new File(root.toString()).listFiles(
      (dir, name) -> name.substring(0, name.lastIndexOf(".")).equals(id.toString())
    );
    if (foundFiles.length == 0) throw new FileNotFoundException("Could not find any files with the name " + id.toString());
    Path file = root.resolve(foundFiles[0].getName());

    if (file == null) throw new IOException("Found file is not valid");

    Resource resource = new UrlResource(file.toUri());

    if (resource.exists() || resource.isReadable()) {
      return resource;
    } else {
      throw new IOException("The file is not readable");
    }
  }

  @Override
  public void deleteFile(Long id) throws IOException {
    Path file = root.resolve(
      new File(root.toString())
        .listFiles(
          new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
              return name.substring(0, name.lastIndexOf(".")).equals(id.toString());
            }
          }
        )[0].getName()
    );

    if (file == null) throw new IOException("Could not read file");

    Files.delete(file);
  }

  @Override
  public Stream<Path> loadFiles(List<Long> fileIds) throws IOException {
    return Files
      .walk(root, 1)
      .filter(path -> fileIds.contains(Long.parseLong(path.getFileName().toString())));
  }
}
