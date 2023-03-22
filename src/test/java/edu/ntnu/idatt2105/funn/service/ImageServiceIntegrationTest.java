package edu.ntnu.idatt2105.placeholder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.repository.file.ImageRepository;
import edu.ntnu.idatt2105.funn.service.file.ImageService;
import edu.ntnu.idatt2105.funn.service.file.ImageServiceImpl;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ImageServiceIntegrationTest {

  @TestConfiguration
  static class ImageServiceIntegrationTestContextConfiguration {

    @Bean
    public ImageService imageService() {
      return new ImageServiceImpl();
    }
  }

  @Autowired
  private ImageService imageService;

  @MockBean
  private ImageRepository imageRepository;

  private Image existingImage;

  private Image newImage;

  @Before
  public void setUp() {
    existingImage = Image.builder().id(1L).alt("picture 1").build();

    newImage = Image.builder().id(2L).alt("picture 2").build();

    when(imageRepository.existsById(existingImage.getId())).thenReturn(true);
    when(imageRepository.existsById(newImage.getId())).thenReturn(false);

    when(imageRepository.save(existingImage)).thenReturn(existingImage);
    when(imageRepository.save(newImage)).thenReturn(newImage);

    when(imageRepository.findById(existingImage.getId()))
      .thenReturn(java.util.Optional.of(existingImage));
    when(imageRepository.findById(newImage.getId())).thenReturn(java.util.Optional.empty());

    when(imageRepository.findAll()).thenReturn(List.of(existingImage));

    doNothing().when(imageRepository).delete(existingImage);
    doNothing().when(imageRepository).delete(newImage);
  }

  @Test
  public void testFileExistsExistingFile() {
    assertTrue(imageService.fileExists(existingImage));
  }

  @Test
  public void testFileExistsNewFile() {
    assertFalse(imageService.fileExists(newImage));
  }

  @Test
  public void testFileSaveNewFile() {
    Image found;
    try {
      found = imageService.saveFile(newImage);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
      return;
    }
    assertEquals(found.getId(), newImage.getId());
    assertEquals(found.getAlt(), newImage.getAlt());
  }

  @Test
  public void testFileSaveExistingFile() {
    try {
      imageService.saveFile(existingImage);
      fail();
    } catch (Exception e) {
      return;
    }
  }

  @Test
  public void testGetFileExistingFile() {
    Image found;
    try {
      found = imageService.getFile(existingImage.getId());
    } catch (Exception e) {
      fail();
      return;
    }
    assertEquals(found.getId(), existingImage.getId());
    assertEquals(found.getAlt(), existingImage.getAlt());
  }

  @Test
  public void testGetFileNewFile() {
    try {
      imageService.getFile(newImage.getId());
      fail();
    } catch (Exception e) {
      return;
    }
  }

  @Test
  public void testUpdateFileExistingFile() {
    Image found;
    try {
      found = imageService.updateFile(existingImage);
    } catch (Exception e) {
      fail();
      return;
    }
    assertEquals(found.getId(), existingImage.getId());
    assertEquals(found.getAlt(), existingImage.getAlt());
  }

  @Test
  public void testUpdateFileNewFile() {
    try {
      imageService.updateFile(newImage);
      fail();
    } catch (Exception e) {
      return;
    }
  }

  @Test
  public void testDeleteFileExistingFile() {
    try {
      imageService.deleteFile(existingImage);
    } catch (Exception e) {
      fail();
      return;
    }
  }

  @Test
  public void testDeleteFileNewFile() {
    try {
      imageService.deleteFile(newImage);
      fail();
    } catch (Exception e) {
      return;
    }
  }

  @Test
  public void testGetAllFiles() {
    List<Image> found;
    try {
      found = imageService.getAllFiles();
    } catch (Exception e) {
      fail();
      return;
    }
    assertEquals(found.size(), 1);
    assertEquals(found.get(0).getId(), existingImage.getId());
    assertEquals(found.get(0).getAlt(), existingImage.getAlt());
  }
}
