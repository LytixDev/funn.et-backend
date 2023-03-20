package edu.ntnu.idatt2105.placeholder.repository.file;

import edu.ntnu.idatt2105.placeholder.model.file.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {}
