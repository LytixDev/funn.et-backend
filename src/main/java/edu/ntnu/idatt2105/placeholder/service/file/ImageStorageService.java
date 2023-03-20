package edu.ntnu.idatt2105.placeholder.service.file;

import edu.ntnu.idatt2105.placeholder.model.file.Image;
import edu.ntnu.idatt2105.placeholder.service.listing.ListingService;
import edu.ntnu.idatt2105.placeholder.service.listing.ListingServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService implements FileStorageService {

  private final Path root = Paths.get("src/main/resources/images");

  private final ImageService imageService;

  @Autowired
  public ImageStorageService(ImageService imageService) {
    this.imageService = imageService;
  }

  @Override
  public void init() throws IOException {
    Files.createDirectory(root);
  }

  @Override
  public void store(MultipartFile file) throws IOException {
    if (
      file.getContentType() != "image/jpeg" &&
      file.getContentType() != "image/png"
    ) {
      throw new IOException("Only JPG and PNG files are allowed");
    }
    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
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
  public Stream<Path> loadListingFiles(Long listingId) throws IOException {
    ListingService listingService = new ListingServiceImpl();
    List<Long> imageIds = listingService
      .getListing(listingId)
      .getImages()
      .stream()
      .map(Image::getId)
      .collect(Collectors.toList());
    return Files
      .walk(root, 1)
      .filter(path ->
        imageIds.contains(Long.parseLong(path.getFileName().toString()))
      );
  }
}
