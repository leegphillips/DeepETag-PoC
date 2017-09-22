package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilterIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void restTemplateCannotDoPatch() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    public void eTagPresent() {
        SKU sku = random(SKU.class, "id");
        ResponseEntity<SKU> responseEntity = restTemplate.postForEntity("/sku", sku, SKU.class);
        ResponseEntity<SKU> responseEntity2 = restTemplate.getForEntity("/sku/" + sku.getId(), SKU.class);
        assertNotNull(responseEntity2.getHeaders().get(HttpHeaders.ETAG));
    }
}
