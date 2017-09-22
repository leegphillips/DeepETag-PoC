package com.github.leegphillips.deepetagpoc;


import com.github.leegphillips.deepetagpoc.model.SKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SKUController {

    @Autowired
    private SKUDao skuDao;

    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    public ResponseEntity<SKU> createSKU(@RequestBody SKU sku) {
        skuDao.save(sku);
        return new ResponseEntity<>(sku, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/sku/{id}")
    public SKU fetchSKU(@PathVariable String id) {
        return skuDao.get(id);
    }

    @RequestMapping(value = "/sku", method = RequestMethod.PATCH)
    public ResponseEntity<SKU> patchSKU(@RequestBody SKU sku) {
        skuDao.merge(sku);
        return new ResponseEntity<>(sku, HttpStatus.OK);
    }
}
