package com.github.leegphillips.DeepETagPoC;


import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SKUController {
    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    public ResponseEntity<?> createSKU(@RequestBody SKU sku) {
        return new ResponseEntity<>(sku, HttpStatus.CREATED);
    }

    @RequestMapping(value = "sku/{id}")
    public SKU fetchSKU(@PathVariable Long id) {
        return new SKU();
    }
}
