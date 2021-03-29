package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
