package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Customer;

import java.util.List;

public interface ICustomerService {

    List<Customer> getAll();

    Customer getById(Long id);

    void save(Customer customer);

    boolean update(Customer customer, Long id);

    void delete(Long id);
}
