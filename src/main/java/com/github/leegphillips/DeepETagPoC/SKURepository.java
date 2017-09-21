package com.github.leegphillips.DeepETagPoC;

import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SKURepository extends MongoRepository<SKU, Long> {
}
