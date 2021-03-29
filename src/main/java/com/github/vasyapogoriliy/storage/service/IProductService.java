package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAll();

    Product getById(Long id);

    boolean save(Product product);

    boolean update(Product product, Long id);

    void delete(Long id);

}
