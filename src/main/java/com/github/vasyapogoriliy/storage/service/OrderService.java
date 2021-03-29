package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Customer;
import com.github.vasyapogoriliy.storage.model.Order;
import com.github.vasyapogoriliy.storage.repository.ICustomerRepository;
import com.github.vasyapogoriliy.storage.repository.IOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;

    public OrderService(IOrderRepository orderRepository, ICustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Order> getAll(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Order findByIdAndCustomerId(Long orderId, Long customerId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId).orElse(null);
    }

    @Override
    public boolean save(Order order, Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        Date date = new Date();

        order.setDate(date.toString());
        order.setCustomer(customer);
        orderRepository.save(order);

        return true;
    }

    @Override
    public void delete(Order orderToDelete) {
        orderRepository.delete(orderToDelete);
    }
}
