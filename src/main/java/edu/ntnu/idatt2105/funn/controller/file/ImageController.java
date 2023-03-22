package edu.ntnu.idatt2105.funn.controller.file;

import edu.ntnu.idatt2105.funn.dto.file.ImageResponseDTO;
import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.file.ImageServiceImpl;
import edu.ntnu.idatt2105.funn.service.file.ImageStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/public/image")
@EnableAutoConfiguration
public class ImageController {

  private final ImageService imageService;

  private final ImageStorageService imageStorageService;

  @Autowired
  public ImageController(ImageServiceImpl imageService, ImageStorageService imageStorageService) {
    this.imageService = imageService;
    this.imageStorageService = imageStorageService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImageResponseDTO> getImage(
    @PathVariable Long id,
    HttpServletRequest request
  ) {
    Image image;

    Resource resource;

    ImageResponseDTO dto = new ImageResponseDTO();

    try {
      image = imageService.getFile(id);
    } catch (FileNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    dto.setAlt(image.getAlt());

    try {
      resource = imageStorageService.loadFile(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    dto.setImage(resource);

    return ResponseEntity.ok(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteImage(@PathVariable Long id) {
    try {
      imageService.deleteFile(id);
    } catch (FileNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    try {
      imageStorageService.deleteFile(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().build();
  }
}
