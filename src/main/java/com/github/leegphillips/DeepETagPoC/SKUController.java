package com.github.leegphillips.DeepETagPoC;


import com.github.leegphillips.DeepETagPoC.model.SKU;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SKUController {
    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    public ResponseEntity<?> createSKU(@RequestBody SKU sku) throws URISyntaxException {
        return ResponseEntity.created(new URI("test")).build();
    }
}
