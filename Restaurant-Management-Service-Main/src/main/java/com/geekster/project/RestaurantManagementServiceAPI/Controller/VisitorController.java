package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Visitor;
import com.geekster.project.RestaurantManagementServiceAPI.Service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpVisitor(@Valid @RequestBody Visitor visitor) {
        visitorService.signUpVisitor(visitor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Visitor registered successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    @GetMapping("/{visitorId}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Integer visitorId) {
        return ResponseEntity.ok(visitorService.getVisitorById(visitorId));
    }

    @PutMapping("/{visitorId}")
    public ResponseEntity<String> updateVisitor(@PathVariable Integer visitorId,
                                                @Valid @RequestBody Visitor visitor) {
        visitorService.updateVisitor(visitorId, visitor);
        return ResponseEntity.ok("Visitor updated successfully");
    }

    @DeleteMapping("/{visitorId}")
    public ResponseEntity<String> deleteVisitor(@PathVariable Integer visitorId) {
        visitorService.deleteVisitor(visitorId);
        return ResponseEntity.ok("Visitor deleted successfully");
    }
}
