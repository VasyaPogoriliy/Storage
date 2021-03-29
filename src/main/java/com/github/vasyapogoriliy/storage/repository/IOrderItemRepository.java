package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(Long orderId);

    boolean existsByIdAndOrderId(Long orderItemId, Long orderId);

}
