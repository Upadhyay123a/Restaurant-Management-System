package com.geekster.project.RestaurantManagementServiceAPI.Service;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Food;
import com.geekster.project.RestaurantManagementServiceAPI.Model.Order;
import com.geekster.project.RestaurantManagementServiceAPI.Model.User;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IFoodRepo;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IOrderRepo;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IUserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private IOrderRepo orderRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IFoodRepo foodRepo;

    public Order createOrder(Order order) {

        Integer userId = order.getCustomer().getUserId();
        Integer foodId = order.getFood().getFoodId();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        order.setCustomer(user);
        order.setFood(food);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepo.save(order);
    }

    public Order updateOrder(Integer orderId, Order updatedOrder) {
        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        existingOrder.setOrderQuantity(updatedOrder.getOrderQuantity());
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());

        return orderRepo.save(existingOrder);
    }

    public void deleteOrder(Integer orderId) {
        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        orderRepo.delete(existingOrder);
    }

    public Order getOrderById(Integer orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
