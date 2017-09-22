package com.github.leegphillips.deepetagpoc;

import com.github.leegphillips.deepetagpoc.model.SKU;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SKURepository extends MongoRepository<SKU, Long> {
}
