package pl.piomin.services.versioning;

import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTests {

    private WebApplicationContext context;
    private RestTestClient restTestClient;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context).build();
    }

    @Test
    @Order(1)
    void addV0() {
        restTestClient.post().uri("/persons/v1.1").body(Instancio.create(PersonOld.class)).exchange().
                expectBody(PersonOld.class)
                .value(personOld -> assertNotNull(personOld.getId()));
    }

    @Test
    @Order(2)
    void addV2() {
        restTestClient.post().uri("/persons/v1.2").body(Instancio.create(PersonCurrent.class)).exchange().
                expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }

    @Test
    @Order(3)
    void findByIdV0() {
        restTestClient.get().uri("/persons/v1.0/{id}", 1).exchange()
                .expectBody(PersonOld.class)
                .value(personOld -> assertNotNull(personOld.getId()));
    }

    @Test
    @Order(3)
    void findByIdV2() {
        restTestClient.get().uri("/persons/v1.2/{id}", 2).exchange()
                .expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }

    @Test
    @Order(3)
    void findByIdV2ToV1Compability() {
        restTestClient.get().uri("/persons/v1.2/{id}", 1).exchange()
                .expectBody(PersonCurrent.class)
                .value(personCurrent -> assertNotNull(personCurrent.getId()))
                .value(personCurrent -> assertTrue(personCurrent.getAge() > 0));
    }
}
