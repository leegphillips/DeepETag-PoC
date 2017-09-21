package com.github.leegphillips.DeepETagPoC;

import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestSKUIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createFetchPersists() {
        SKU sku = random(SKU.class, "id");
        ResponseEntity<SKU> responseEntity1 = restTemplate.postForEntity("/sku", sku, SKU.class);
        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        SKU created = responseEntity1.getBody();
        assertThat(sku, new ReflectionEquals(created, "id"));

        ResponseEntity<SKU> responseEntity2 = restTemplate.getForEntity("/sku/" + created.getId(), SKU.class);
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());

        assertEquals(created, responseEntity2.getBody());
    }
}
