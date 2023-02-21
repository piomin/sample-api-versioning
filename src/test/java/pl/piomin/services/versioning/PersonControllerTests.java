package pl.piomin.services.versioning;

import org.instancio.Instancio;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void addV0() {
        PersonOld p = restTemplate.postForObject("/person/v1.1", Instancio.create(PersonOld.class), PersonOld.class);
        assertNotNull(p);
        assertNotNull(p.getId());
    }

    @Test
    @Order(2)
    void addV2() {
        PersonCurrent p = restTemplate.postForObject("/person/v1.2", Instancio.create(PersonCurrent.class), PersonCurrent.class);
        assertNotNull(p);
        assertNotNull(p.getId());
        assertTrue(p.getAge() > 0);
    }

    @Test
    @Order(3)
    void findByIdV0() {
        PersonOld p = restTemplate.getForObject("/person/v1.0/{id}", PersonOld.class, 1);
        assertNotNull(p);
        assertNotNull(p.getId());
    }

    @Test
    @Order(3)
    void findByIdV2() {
        PersonCurrent p = restTemplate.getForObject("/person/v1.2/{id}", PersonCurrent.class, 2);
        assertNotNull(p);
        assertNotNull(p.getId());
        assertTrue(p.getAge() > 0);
    }

    @Test
    @Order(3)
    void findByIdV2ToV1Compability() {
        PersonCurrent p = restTemplate.getForObject("/person/v1.2/{id}", PersonCurrent.class, 1);
        assertNotNull(p);
        assertNotNull(p.getId());
        assertTrue(p.getAge() > 0);
    }
}
