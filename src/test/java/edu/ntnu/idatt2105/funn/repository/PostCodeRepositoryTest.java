package edu.ntnu.idatt2105.funn.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.repository.location.PostCodeRepository;
import java.util.HashSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostCodeRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  PostCodeRepository postCodeRepository;

  @Test
  public void testFindByPostCode() {
    PostCode postCode = new PostCode(1445, "city", new HashSet<>());

    entityManager.persist(postCode);

    entityManager.flush();

    String found = postCodeRepository.findCitiesByPostCode(postCode.getPostCode()).get().get(0);

    assertEquals(postCode.getCity(), found);
  }

  @Test
  public void testFindByCity() {
    PostCode postCode = new PostCode(1445, "city", new HashSet<>());

    entityManager.persist(postCode);

    entityManager.flush();

    int found = postCodeRepository.findPostCodesByCity(postCode.getCity()).get().get(0);

    assertEquals(postCode.getPostCode(), found);
  }
}
