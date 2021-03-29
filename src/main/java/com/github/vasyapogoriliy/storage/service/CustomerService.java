package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Customer;
import com.github.vasyapogoriliy.storage.repository.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean update(Customer customer, Long id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }

        customer.setId(id);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
