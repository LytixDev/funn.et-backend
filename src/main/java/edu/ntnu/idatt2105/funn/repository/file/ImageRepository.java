package edu.ntnu.idatt2105.funn.repository.file;

import edu.ntnu.idatt2105.funn.model.file.Image;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<List<Image>> findAllByListing(Long listing);
}
