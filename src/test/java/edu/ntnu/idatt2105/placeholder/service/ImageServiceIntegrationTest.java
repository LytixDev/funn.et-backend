package edu.ntnu.idatt2105.placeholder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ntnu.idatt2105.placeholder.model.file.Image;
import edu.ntnu.idatt2105.placeholder.repository.file.ImageRepository;
import edu.ntnu.idatt2105.placeholder.service.file.FileService;
import edu.ntnu.idatt2105.placeholder.service.file.ImageServiceImpl;

@RunWith(SpringRunner.class)
public class ImageServiceIntegrationTest {
    
    @TestConfiguration
    static class ImageServiceIntegrationTestContextConfiguration {
        @Bean
        public FileService<Image> fileService() {
            return new ImageServiceImpl();
        }
    }

    @Autowired
    private FileService<Image> fileService;

    @MockBean
    private ImageRepository imageRepository;

    private Image existingImage;

    private Image newImage;

    @Before
    public void setUp() {
        existingImage = Image.builder().id(1L).url("images/1.jpg").build();
        
        newImage = Image.builder().id(2L).url("images/2.jpg").build();
        
        when(imageRepository.existsById(existingImage.getId())).thenReturn(true);
        when(imageRepository.existsById(newImage.getId())).thenReturn(false);

        when(imageRepository.save(existingImage)).thenReturn(existingImage);
        when(imageRepository.save(newImage)).thenReturn(newImage);

        when(imageRepository.findById(existingImage.getId())).thenReturn(java.util.Optional.of(existingImage));
        when(imageRepository.findById(newImage.getId())).thenReturn(java.util.Optional.empty());

        when(imageRepository.findAll()).thenReturn(List.of(existingImage));
        
        doNothing().when(imageRepository).delete(existingImage);
        doNothing().when(imageRepository).delete(newImage);
    }

    @Test
    public void testFileExistsExistingFile() {
        assertTrue(fileService.fileExists(existingImage));
    }

    @Test
    public void testFileExistsNewFile() {
        assertFalse(fileService.fileExists(newImage));
    }

    @Test
    public void testFileSaveNewFile() {
        Image found;
        try {
            found = fileService.saveFile(newImage);
        } catch (Exception e) {
            fail();
            return;
        }
        assertEquals(found.getId(), newImage.getId());
        assertEquals(found.getUrl(), newImage.getUrl());
    }

    @Test
    public void testFileSaveExistingFile() {
        try {
            fileService.saveFile(existingImage);
            fail();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testGetFileExistingFile() {
        Image found;
        try {
            found = fileService.getFile(existingImage.getId());
        } catch (Exception e) {
            fail();
            return;
        }
        assertEquals(found.getId(), existingImage.getId());
        assertEquals(found.getUrl(), existingImage.getUrl());
    }

    @Test
    public void testGetFileNewFile() {
        try {
            fileService.getFile(newImage.getId());
            fail();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testUpdateFileExistingFile() {
        Image found;
        try {
            found = fileService.updateFile(existingImage);
        } catch (Exception e) {
            fail();
            return;
        }
        assertEquals(found.getId(), existingImage.getId());
        assertEquals(found.getUrl(), existingImage.getUrl());
    }

    @Test
    public void testUpdateFileNewFile() {
        try {
            fileService.updateFile(newImage);
            fail();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testDeleteFileExistingFile() {
        try {
            fileService.deleteFile(existingImage);
        } catch (Exception e) {
            fail();
            return;
        }
    }

    @Test
    public void testDeleteFileNewFile() {
        try {
            fileService.deleteFile(newImage);
            fail();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testGetAllFiles() {
        List<Image> found;
        try {
            found = fileService.getAllFiles();
        } catch (Exception e) {
            fail();
            return;
        }
        assertEquals(found.size(), 1);
        assertEquals(found.get(0).getId(), existingImage.getId());
        assertEquals(found.get(0).getUrl(), existingImage.getUrl());
    }
}
