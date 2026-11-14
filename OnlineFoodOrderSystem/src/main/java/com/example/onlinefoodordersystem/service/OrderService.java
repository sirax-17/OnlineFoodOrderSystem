
package com.example.onlinefoodordersystem.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.onlinefoodordersystem.model.FoodOrder;
import com.example.onlinefoodordersystem.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // CREATE - Add Order
    public FoodOrder addOrder(FoodOrder order) {
        return orderRepository.save(order);
    }

    // READ - Get All Orders
    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // READ - Get Order By ID
    public FoodOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"));
    }

    // UPDATE - Update Complete Order
    public FoodOrder updateOrder(Long id, FoodOrder newOrder) {

        FoodOrder existingOrder = getOrderById(id);

        existingOrder.setCustomerName(newOrder.getCustomerName());
        existingOrder.setFoodItem(newOrder.getFoodItem());
        existingOrder.setQuantity(newOrder.getQuantity());
        existingOrder.setPrice(newOrder.getPrice());

        return orderRepository.save(existingOrder);
    }

    // PATCH - Partial Update
    public FoodOrder partialUpdate(Long id, Map<String, Object> updates) {

        FoodOrder existingOrder = getOrderById(id);

        if (updates.containsKey("customerName")) {
            Object value = updates.get("customerName");

            if (!(value instanceof String) ||
                    ((String) value).isBlank()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Customer name is required");
            }

            existingOrder.setCustomerName((String) value);
        }

        if (updates.containsKey("foodItem")) {
            Object value = updates.get("foodItem");

            if (!(value instanceof String) ||
                    ((String) value).isBlank()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Food item cannot be empty");
            }

            existingOrder.setFoodItem((String) value);
        }

        if (updates.containsKey("quantity")) {
            Object value = updates.get("quantity");

            if (!(value instanceof Number) ||
                    ((Number) value).intValue() < 1 ||
                    ((Number) value).doubleValue() !=
                    ((Number) value).intValue()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be at least 1 and a whole number");
            }

            existingOrder.setQuantity(((Number) value).intValue());
        }

        if (updates.containsKey("price")) {
            Object value = updates.get("price");

            if (!(value instanceof Number) ||
                    !Double.isFinite(((Number) value).doubleValue()) ||
                    ((Number) value).doubleValue() < 0) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Price must be a valid non-negative number");
            }

            existingOrder.setPrice(((Number) value).doubleValue());
        }

        return orderRepository.save(existingOrder);
    }

    // DELETE - Delete Order
    public String deleteOrder(Long id) {

        FoodOrder existingOrder = getOrderById(id);

        orderRepository.delete(existingOrder);

        return "Order deleted successfully";
    }
}