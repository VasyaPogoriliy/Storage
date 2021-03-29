package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.Product;
import com.github.vasyapogoriliy.storage.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();

        if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        if (id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Product product = productService.getById(id);

        if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) {
        if (product == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!productService.save(product)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody @Valid Product product) {
        if (product == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!productService.update(product, id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        if (productService.getById(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
