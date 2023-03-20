package edu.ntnu.idatt2105.placeholder.repository.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository<T> extends JpaRepository<T, Long> {}
