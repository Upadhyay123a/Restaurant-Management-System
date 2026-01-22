package com.geekster.project.RestaurantManagementServiceAPI.Repository;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IOrderRepo extends JpaRepository<Order, Integer> {
    
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}