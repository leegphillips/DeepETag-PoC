package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.junit.Before;
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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestSKUIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SKURepository skuRepository;

    @Before
    public void clearRepository() {
        skuRepository.deleteAll();
    }

    @PostConstruct
    public void restTemplateCannotDoPatch() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    public void persistPopulatesID() {
        SKU sku = random(SKU.class, "id");
        assertNull(sku.getId());
        SKU created = persistSKUViaRest(sku);
        assertNotNull(created.getId());
    }

    @Test
    public void createFetchPersists() {
        SKU sku = random(SKU.class, "id");
        SKU created = persistSKUViaRest(sku);
        checkFetchSKUMatchesInput(created);
    }

    @Test
    public void checkPatchUpdates() {
        SKU sku = random(SKU.class, "id");
        SKU created = persistSKUViaRest(sku);

        created.setCreated(new Date());
        ResponseEntity<SKU> patchResponse = doPatch("/sku", created);
        assertEquals(HttpStatus.OK, patchResponse.getStatusCode());

        checkFetchSKUMatchesInput(created);
    }

    @Test
    public void invalidFetchErrors() {
        ResponseEntity<SKU> responseEntity = restTemplate.getForEntity("/sku/null", SKU.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private void checkFetchSKUMatchesInput(SKU sku) {
        ResponseEntity<SKU> responseEntity2 = restTemplate.getForEntity("/sku/" + sku.getId(), SKU.class);
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
        assertEquals(sku, responseEntity2.getBody());
    }

    private SKU persistSKUViaRest(SKU sku) {
        ResponseEntity<SKU> responseEntity1 = restTemplate.postForEntity("/sku", sku, SKU.class);
        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());

        SKU created = responseEntity1.getBody();
        assertTrue(new ReflectionEquals(created, "id").matches(sku));

        return created;
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
