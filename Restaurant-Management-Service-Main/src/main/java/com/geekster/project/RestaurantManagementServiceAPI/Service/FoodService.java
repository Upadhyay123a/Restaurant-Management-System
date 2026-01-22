package com.geekster.project.RestaurantManagementServiceAPI.Service;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Food;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IFoodRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {
    @Autowired
    private IFoodRepo foodRepo;

    public Food createFood(Food food) {
        // Add new food item to the menu
        return foodRepo.save(food);
    }

    public Food updateFood(Integer foodId, Food updatedFood) {
        Food existingFood = foodRepo.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        // Update food information in the database
        existingFood.setFoodTitle(updatedFood.getFoodTitle());
        existingFood.setFoodDesc(updatedFood.getFoodDesc());
        existingFood.setFoodType(updatedFood.getFoodType());
        // Update food details with new information

        return foodRepo.save(existingFood);
    }

    public void deleteFood(Integer foodId) {
        Food existingFood = foodRepo.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        foodRepo.delete(existingFood);
    }

    public Food getFoodById(Integer foodId) {
        return foodRepo.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));
    }

    public List<Food> getAllFoodItems() {
        return foodRepo.findAll();
    }
}