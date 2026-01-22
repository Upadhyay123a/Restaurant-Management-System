package com.geekster.project.RestaurantManagementServiceAPI.Controller;

/*
 * Handles all food management operations for the restaurant
 * Including creating food items, updating menu items, and retrieving food inventory
 */

import com.geekster.project.RestaurantManagementServiceAPI.Model.Food;
import com.geekster.project.RestaurantManagementServiceAPI.Service.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fooditems")
public class FoodController {

    @Autowired
    private FoodService foodService;

    /* Add new food item to the restaurant menu */
    public ResponseEntity<String> createFoodItem(@Valid @RequestBody Food foodItem) {
        foodService.createFood(foodItem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Food item created successfully");
    }

    /* Get all food items from the menu */
    public ResponseEntity<List<Food>> getAllFoodItems() {
        return ResponseEntity.ok(foodService.getAllFoodItems());
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodItemById(@PathVariable Integer foodId) {
        return ResponseEntity.ok(foodService.getFoodById(foodId));
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<String> updateFoodItem(@PathVariable Integer foodId,
                                                 @Valid @RequestBody Food food) {
        foodService.updateFood(foodId, food);
        return ResponseEntity.ok("Food item updated successfully");
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable Integer foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.ok("Food item deleted successfully");
    }
}
