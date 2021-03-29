package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.OrderItem;

import java.util.List;

public interface IOrderItemService {

    List<OrderItem> getAll(Long orderId, Long customerId);

    OrderItem getById(Long orderItemId, Long orderId, Long customerId);

    boolean save(OrderItem orderItem, Long orderId, Long customerId);

    boolean update(OrderItem orderItem, Long orderItemId, Long orderId, Long customerId);

    boolean delete(OrderItem orderItemToDelete);

}
