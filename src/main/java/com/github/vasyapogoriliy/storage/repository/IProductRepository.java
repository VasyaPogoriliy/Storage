package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameAndWeightAndPrice(String name, int weight, int price);

}
