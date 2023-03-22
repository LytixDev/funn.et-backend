package edu.ntnu.idatt2105.funn.service.file;

import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.file.FileNotFoundException;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * Interface for file services.
 * @param <T> The type of file.
 * @author Callum G.
 * @version 1.0 - 20.03.2023
 */
@Service
public interface FileService<T> {
  boolean fileExists(@NonNull T file) throws NullPointerException;

  T saveFile(@NonNull T file) throws DatabaseException, NullPointerException;

  T getFile(Long id) throws FileNotFoundException, DatabaseException;

  T updateFile(@NonNull T file)
    throws FileNotFoundException, DatabaseException, NullPointerException;

  void deleteFile(@NonNull T file)
    throws FileNotFoundException, DatabaseException, NullPointerException;

  void deleteFile(Long id) throws FileNotFoundException, DatabaseException;

  List<T> getAllFiles();
}
