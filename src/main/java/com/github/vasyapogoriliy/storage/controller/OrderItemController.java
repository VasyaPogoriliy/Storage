package com.github.vasyapogoriliy.storage.controller;

import com.github.vasyapogoriliy.storage.model.OrderItem;
import com.github.vasyapogoriliy.storage.service.OrderItemService;
import com.github.vasyapogoriliy.storage.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}/orders/{orderId}/products")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    public OrderItemController(OrderItemService orderItemService,
                               OrderService orderService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<List<OrderItem>> getAllOrderItems(@PathVariable("orderId") Long orderId,
                                                            @PathVariable("customerId") Long customerId) {
        if (orderService.findByIdAndCustomerId(orderId, customerId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<OrderItem> orderItems = orderItemService.getAll(orderId, customerId);

        if (orderItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable("orderItemId") Long orderItemId,
                                                  @PathVariable("orderId") Long orderId,
                                                  @PathVariable("customerId") Long customerId) {
        if (orderService.findByIdAndCustomerId(orderId, customerId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (orderItemId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        OrderItem orderItem = orderItemService.getById(orderItemId, orderId, customerId);

        if (orderItem == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody @Valid OrderItem orderItem,
                                                  @PathVariable("orderId") Long orderId,
                                                  @PathVariable("customerId") Long customerId) {
        if (orderService.findByIdAndCustomerId(orderId, customerId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (orderItem == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!orderItemService.save(orderItem, orderId, customerId))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable("orderItemId") Long orderItemId,
                                                         @PathVariable("orderId") Long orderId,
                                                         @PathVariable("customerId") Long customerId,
                                                         @RequestBody @Valid OrderItem orderItem) {
        if (orderService.findByIdAndCustomerId(orderId, customerId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (orderItem == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!orderItemService.update(orderItem, orderItemId, orderId, customerId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<OrderItem> deleteOrderItem(@PathVariable("orderItemId") Long orderItemId,
                                                         @PathVariable("orderId") Long orderId,
                                                         @PathVariable("customerId") Long customerId) {
        if (orderService.findByIdAndCustomerId(orderId, customerId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OrderItem orderItemToDelete = orderItemService.getById(orderItemId, orderId, customerId);

        if (orderItemToDelete == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (!orderItemService.delete(orderItemToDelete)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
