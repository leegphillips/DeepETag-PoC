package com.github.leegphillips.DeepETagPoC;

import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestSKUIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createSKU() {
        ResponseEntity<SKU> responseEntity = restTemplate.postForEntity("/sku", random(SKU.class), SKU.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void fetchSKU() {
        ResponseEntity<SKU> responseEntity = restTemplate.getForEntity("/sku/1", SKU.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
