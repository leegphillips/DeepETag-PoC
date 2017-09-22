package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class SKUDao {
    @Autowired
    private SKURepository skuRepository;

    public void save(SKU sku) {
        skuRepository.save(sku);
    }

    @Cacheable("skus")
    public SKU get(String id) {
        SKU sku = new SKU();
        sku.setId(id);
        return skuRepository.findOne(Example.of(sku));
    }

    // ConcurrentMapCache.evict cannot handle null ids so we need a separate call to the save
    @CacheEvict(value = "skus", key = "#sku.id")
    public void merge(SKU sku) {
        skuRepository.save(sku);
    }
}
