package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Product;
import com.github.vasyapogoriliy.storage.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(Product product) {
        List<Product> products = productRepository.findAll();

        for (Product productToCompare : products) {
            if (productToCompare.getName().equals(product.getName())) return false;
        }

        productRepository.save(product);

        return true;
    }

    @Override
    public boolean update(Product product, Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }

        product.setId(id);

        List<Product> products = productRepository.findAll();

        for (Product productToCompare : products) {
            if (!productToCompare.getName().equals(product.getName())) {
                continue;
            }
            if (productToCompare.getWeight() == product.getWeight() && productToCompare.getPrice() == product.getPrice()) {
                return false;
            }
            if (!productToCompare.getId().equals(product.getId())) {
                return false;
            }
        }

        productRepository.save(product);

        return true;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
