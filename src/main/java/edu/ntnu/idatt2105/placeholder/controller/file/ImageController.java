package edu.ntnu.idatt2105.placeholder.controller.file;

import edu.ntnu.idatt2105.placeholder.dto.file.ImageDTO;
import edu.ntnu.idatt2105.placeholder.exceptions.file.FileNotFoundException;
import edu.ntnu.idatt2105.placeholder.service.file.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/public/image")
@EnableAutoConfiguration
@CrossOrigin
public class ImageController {

  private final ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
    try {
      return imageService.getFile(id);
    } catch (FileNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
