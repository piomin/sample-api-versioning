package pl.piomin.services.versioning;

import org.instancio.Instancio;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.piomin.services.versioning.model.PersonCurrent;
import pl.piomin.services.versioning.model.PersonOld;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerWithHeadersTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void addV0() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VERSION", "v1.0");
        HttpEntity<PersonOld> httpEntity = new HttpEntity<>(Instancio.create(PersonOld.class), headers);
        ResponseEntity<PersonOld> p = restTemplate.exchange("/persons-via-headers", HttpMethod.POST, httpEntity, PersonOld.class);
        assertTrue(p.getStatusCode().is2xxSuccessful());
        assertNotNull(p.getBody());
        assertNotNull(p.getBody().getId());
    }

    @Test
    @Order(2)
    void addV2() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VERSION", "v1.2");
        HttpEntity<PersonCurrent> httpEntity = new HttpEntity<>(Instancio.create(PersonCurrent.class), headers);
        ResponseEntity<PersonCurrent> p = restTemplate.exchange("/persons-via-headers", HttpMethod.POST, httpEntity, PersonCurrent.class);
        assertTrue(p.getStatusCode().is2xxSuccessful());
        assertNotNull(p.getBody());
        assertNotNull(p.getBody().getId());
        assertTrue(p.getBody().getAge() > 0);
    }

    @Test
    @Order(3)
    void findByIdV0() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VERSION", "v1.0");
        HttpEntity<PersonCurrent> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<PersonOld> p = restTemplate.exchange("/persons-via-headers/{id}", HttpMethod.GET, httpEntity, PersonOld.class, 1);
        assertTrue(p.getStatusCode().is2xxSuccessful());
        assertNotNull(p.getBody());
        assertNotNull(p.getBody().getId());
    }

    @Test
    @Order(3)
    void findByIdV2() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VERSION", "v1.2");
        HttpEntity<PersonCurrent> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<PersonCurrent> p = restTemplate.exchange("/persons-via-headers/{id}", HttpMethod.GET, httpEntity, PersonCurrent.class, 2);
        assertTrue(p.getStatusCode().is2xxSuccessful());
        assertNotNull(p.getBody());
        assertNotNull(p.getBody().getId());
        assertTrue(p.getBody().getAge() > 0);
    }

    @Test
    @Order(3)
    void findByIdV2ToV1Compability() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-VERSION", "v1.2");
        HttpEntity<PersonCurrent> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<PersonCurrent> p = restTemplate.exchange("/persons-via-headers/{id}", HttpMethod.GET, httpEntity, PersonCurrent.class, 1);
        assertTrue(p.getStatusCode().is2xxSuccessful());
        assertNotNull(p.getBody());
        assertNotNull(p.getBody().getId());
        assertTrue(p.getBody().getAge() > 0);
    }
}
