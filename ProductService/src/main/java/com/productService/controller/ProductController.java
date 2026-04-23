package com.productService.controller;

import com.productService.dto.ProductReqDto;
import com.productService.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductReqDto productReqDto){

        return productService.addProduct(productReqDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(){

        return productService.getAllProducts();
    }

}
