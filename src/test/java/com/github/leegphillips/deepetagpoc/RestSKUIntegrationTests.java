package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Date;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestSKUIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void restTemplateCannotDoPatch() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    public void createFetchPersists() {
        SKU sku = random(SKU.class, "id");
        ResponseEntity<SKU> responseEntity1 = restTemplate.postForEntity("/sku", sku, SKU.class);
        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        SKU created = responseEntity1.getBody();
        assertTrue(new ReflectionEquals(created, "id").matches(sku));

        ResponseEntity<SKU> responseEntity2 = restTemplate.getForEntity("/sku/" + created.getId(), SKU.class);
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());

        assertEquals(created, responseEntity2.getBody());
    }

    @Test
    public void checkPatchUpdates() {
        SKU sku = random(SKU.class, "id");
        ResponseEntity<SKU> responseEntity1 = restTemplate.postForEntity("/sku", sku, SKU.class);
        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        SKU created = responseEntity1.getBody();
        assertTrue(new ReflectionEquals(created, "id").matches(sku));

        created.setCreated(new Date());
        ResponseEntity<SKU> patchResponse = doPatch("/sku/" + created.getId(), created);
        assertEquals(HttpStatus.ACCEPTED, patchResponse.getStatusCode());
    }

    // RestTemplate cannot currently do PATCH
    private ResponseEntity<SKU> doPatch(String path, SKU update) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "merge-patch+json");
        headers.setContentType(mediaType);

        HttpEntity<SKU> entity = new HttpEntity<>(update, headers);

        return restTemplate.exchange(path, HttpMethod.PATCH, entity, SKU.class);
    }
}
