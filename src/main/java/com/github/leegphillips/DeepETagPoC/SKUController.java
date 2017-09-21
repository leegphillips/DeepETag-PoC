package com.github.leegphillips.DeepETagPoC;


import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SKUController {
    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    public ResponseEntity<?> createSKU(@RequestBody SKU sku) throws URISyntaxException {
        return new ResponseEntity<>(sku, HttpStatus.CREATED);
    }

    @RequestMapping(value = "sku/{id}")
    public SKU fetchSKU(@PathVariable Long id) {
        return new SKU();
    }
}
