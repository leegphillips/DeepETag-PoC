package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DeepETagPoCApplication.class)
public class SKURepositoryTests {

    @Autowired
    private SKURepository skuRepository;

    @Before
    public void clearRepository() {
        skuRepository.deleteAll();
    }

    @Test
    public void persistSetsID() {
        SKU sku = random(SKU.class, "id");
        assertNull(sku.getId());

        skuRepository.save(sku);
        assertNotNull(sku.getId());
    }

    @Test
    public void saveUpdatesState() {
        SKU sku = random(SKU.class, "id");
        skuRepository.save(sku);

        SKU toUpdate = random(SKU.class);
        toUpdate.setId(sku.getId());
        skuRepository.save(toUpdate);

        SKU updated = skuRepository.findOne(Example.of(sku, ExampleMatcher.matchingAny()
                .withMatcher("id", new ExampleMatcher.GenericPropertyMatcher())));
        assertEquals(toUpdate, updated);
    }
}
