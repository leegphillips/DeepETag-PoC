package com.github.leegphillips.DeepETagPoC;


import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SKUController {

    @Autowired
    private SKUDao skuDao;

    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    public ResponseEntity<?> createSKU(@RequestBody SKU sku) {
        skuDao.save(sku);
        return new ResponseEntity<>(sku, HttpStatus.CREATED);
    }

    @RequestMapping(value = "sku/{id}")
    public SKU fetchSKU(@PathVariable String id) {
        return skuDao.get(id);
    }
}
