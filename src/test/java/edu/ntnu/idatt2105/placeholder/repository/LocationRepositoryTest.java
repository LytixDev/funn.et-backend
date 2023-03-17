package edu.ntnu.idatt2105.placeholder.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import edu.ntnu.idatt2105.placeholder.repository.location.LocationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
  
    @Autowired
    private LocationRepository LocationRepository;

    @Test
    public void testFindByPostCode() {
        PostCode postCode = new PostCode("1445", "Droebak");

        Location location = Location.builder().address("address").postCode(postCode).latitude(1.0).longitude(1.0).build();

        entityManager.persist(location);

        entityManager.flush();

        Location found = LocationRepository.findByPostCode(postCode.getPostCode()).get().get(0);

        assertEquals(location.getPostCode(), found.getPostCode());
        assertEquals(location.getAddress(), found.getAddress());
        assertEquals(location.getLatitude(), found.getLatitude());
        assertEquals(location.getLongitude(), found.getLongitude());
    }
}
