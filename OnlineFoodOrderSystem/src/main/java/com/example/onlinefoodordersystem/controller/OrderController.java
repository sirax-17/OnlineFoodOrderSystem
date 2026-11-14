
package com.example.onlinefoodordersystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlinefoodordersystem.model.FoodOrder;
import com.example.onlinefoodordersystem.service.OrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET - Get All Orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    // GET - Get Order By ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // POST - Add Order
    @PostMapping
    public FoodOrder addOrder(@Valid @RequestBody FoodOrder order) {
        return orderService.addOrder(order);
    }

    // PUT - Update Complete Order
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {

        return orderService.updateOrder(id, order);
    }

    // PATCH - Partial Update
    @PatchMapping("/{id}")
    public FoodOrder partialUpdate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return orderService.partialUpdate(id, updates);
    }

    // DELETE - Delete Order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}