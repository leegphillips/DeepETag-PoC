package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DeepETagPoCApplication.class)
public class CacheTests {
    @Autowired
    private SKUController skuController;

    @Autowired
    private SKURepository skuRepository;

    @Before
    public void resetExpectations() {
        reset(skuRepository);
    }

    @Test
    public void checkDoubleFetchOnlyCallsRepoOnce() {
        SKU sku = random(SKU.class);
        skuController.fetchSKU(sku.getId(), null);
        skuController.fetchSKU(sku.getId(), null);
        verify(skuRepository, times(1)).findOne(any(Example.class));
    }

    @Test
    public void checkPatchEvictsCache() {
        SKU sku = random(SKU.class);
        skuController.fetchSKU(sku.getId(), null);
        skuController.patchSKU(sku);
        skuController.fetchSKU(sku.getId(), null);
        verify(skuRepository, times(2)).findOne(any(Example.class));
    }
}
