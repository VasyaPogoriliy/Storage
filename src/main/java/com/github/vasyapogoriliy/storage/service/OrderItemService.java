package com.github.vasyapogoriliy.storage.service;

import com.github.vasyapogoriliy.storage.model.Order;
import com.github.vasyapogoriliy.storage.model.OrderItem;
import com.github.vasyapogoriliy.storage.model.SectionItem;
import com.github.vasyapogoriliy.storage.repository.IOrderItemRepository;
import com.github.vasyapogoriliy.storage.repository.ISectionItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService implements IOrderItemService {

    private final IOrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final SectionItemService sectionItemService;
    private final ISectionItemRepository sectionItemRepository;

    public OrderItemService(IOrderItemRepository orderItemRepository,
                            OrderService orderService,
                            SectionItemService sectionItemService,
                            ISectionItemRepository sectionItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.sectionItemService = sectionItemService;
        this.sectionItemRepository = sectionItemRepository;
    }

    @Override
    public List<OrderItem> getAll(Long orderId, Long customerId) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        return orderItems.stream()
                .filter(i -> i.getOrder().getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public OrderItem getById(Long orderItemId, Long orderId, Long customerId) {
        List<OrderItem> orderItems = getAll(orderId, customerId);
        return orderItems.stream().filter(i -> i.getId().equals(orderItemId)).findFirst().orElse(null);
    }

    @Override
    public boolean save(OrderItem orderItem, Long orderId, Long customerId) {

        SectionItem sectionItem = findSectionItemByOrderItemParam(orderItem);

        if (sectionItem == null) {
            return false;
        }

        int amount = sectionItem.getAmount() - orderItem.getAmount();

        if (amount >= 0) {
            sectionItem.setAmount(amount);
        } else {
            return false;
        }

        saveSectionItem(sectionItem);

        Order order = orderService.findByIdAndCustomerId(orderId, customerId);
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItemToCompare : orderItems) {
            if (orderItemToCompare.getProduct().getName().equals(orderItem.getProduct().getName()) &&
                    orderItemToCompare.getProduct().getWeight() == orderItem.getProduct().getWeight() &&
                    orderItemToCompare.getProduct().getPrice() == orderItem.getProduct().getPrice()) {

                orderItem.setId(orderItemToCompare.getId());
                orderItem.setProduct(orderItemToCompare.getProduct());
                orderItem.setOrder(order);
                orderItem.setAmount(orderItemToCompare.getAmount() + orderItem.getAmount());

                orderItemRepository.delete(orderItemToCompare);
                orderItemRepository.save(orderItem);
                return true;
            }
        }

        orderItem.setOrder(order);
        orderItem.setProduct(sectionItem.getProduct());
        orderItemRepository.save(orderItem);

        return true;
    }

    @Override
    public boolean update(OrderItem orderItem, Long orderItemId, Long orderId, Long customerId) {

        if (!orderItemRepository.existsByIdAndOrderId(orderItemId, orderId)) {
            return false;
        }

        Order order = orderService.findByIdAndCustomerId(orderId, customerId);
        List<OrderItem> orderItems = order.getOrderItems();

        orderItem.setId(orderItemId);
        orderItem.setOrder(order);

        for (OrderItem orderItemToCompare : orderItems) {
            if (orderItemToCompare.getProduct().getName().equals(orderItem.getProduct().getName()) &&
                    orderItemToCompare.getProduct().getWeight() == orderItem.getProduct().getWeight() &&
                    orderItemToCompare.getProduct().getPrice() == orderItem.getProduct().getPrice() &&
                    orderItemToCompare.getAmount() != orderItem.getAmount() &&
                    orderItemToCompare.getId().equals(orderItem.getId())) {

                SectionItem sectionItem = findSectionItemByOrderItemParam(orderItem);

                if (sectionItem == null) {
                    return false;
                }

                int amount = sectionItem.getAmount() - (orderItem.getAmount() - orderItemToCompare.getAmount());

                if (amount >= 0) {
                    sectionItem.setAmount(amount);
                } else {
                    return false;
                }

                saveSectionItem(sectionItem);

                orderItem.setProduct(sectionItem.getProduct());
                orderItemRepository.save(orderItem);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(OrderItem orderItemToDelete) {

        SectionItem sectionItem = findSectionItemByOrderItemParam(orderItemToDelete);

        if (sectionItem == null) {
            return false;
        }

        int amount = sectionItem.getAmount() + orderItemToDelete.getAmount();
        sectionItem.setAmount(amount);

        saveSectionItem(sectionItem);

        orderItemRepository.delete(orderItemToDelete);

        return true;
    }


    private SectionItem findSectionItemByOrderItemParam(OrderItem orderItem) {
        return sectionItemRepository.findByProductNameAndProductWeightAndProductPrice(
                orderItem.getProduct().getName(),
                orderItem.getProduct().getWeight(),
                orderItem.getProduct().getPrice())
                .orElse(null);
    }

    private void saveSectionItem(SectionItem sectionItem) {
        sectionItemService.update(sectionItem,
                sectionItem.getId(),
                sectionItem.getSection().getId(),
                sectionItem.getSection().getStorage().getId());

    }

}
