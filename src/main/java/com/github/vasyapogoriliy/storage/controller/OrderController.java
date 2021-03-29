package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.Order;
import com.github.vasyapogoriliy.storage.service.CustomerService;
import com.github.vasyapogoriliy.storage.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable("customerId") Long customerId) {
        if (customerService.getById(customerId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Order> orders = orderService.getAll(customerId);

        if (orders.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") Long orderId,
                                          @PathVariable("customerId") Long customerId) {
        if (customerService.getById(customerId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (orderId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Order order = orderService.findByIdAndCustomerId(orderId, customerId);

        if (order == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Order> addOrder(@RequestBody @Valid Order order,
                                          @PathVariable("customerId") Long customerId) {
        if (customerService.getById(customerId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (order == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!orderService.save(order, customerId)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("orderId") Long orderId,
                                                 @PathVariable("customerId") Long customerId) {
        if (customerService.getById(customerId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Order orderToDelete = orderService.findByIdAndCustomerId(orderId, customerId);

        if (orderToDelete == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        orderService.delete(orderToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
