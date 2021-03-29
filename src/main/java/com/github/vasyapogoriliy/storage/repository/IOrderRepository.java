package com.github.vasyapogoriliy.storage.repository;

import com.github.vasyapogoriliy.storage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    Optional<Order> findByIdAndCustomerId(Long orderId, Long customerId);

}
