package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Order;

import java.util.List;

public interface IOrderService {

    List<Order> getAll(Long customerId);

    Order findByIdAndCustomerId(Long orderId, Long customerId);

    boolean save(Order order, Long customerId);

    void delete(Order orderToDelete);

}
