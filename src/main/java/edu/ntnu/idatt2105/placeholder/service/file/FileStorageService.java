package edu.ntnu.idatt2105.placeholder.service.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  void init() throws IOException;

  void store(MultipartFile file, Long id) throws IOException;

  Resource loadFile(Long id) throws IOException, MalformedURLException;

  void deleteFile(Long id) throws IOException;

  Stream<Path> loadFiles(List<Long> fileIds) throws IOException;
}
