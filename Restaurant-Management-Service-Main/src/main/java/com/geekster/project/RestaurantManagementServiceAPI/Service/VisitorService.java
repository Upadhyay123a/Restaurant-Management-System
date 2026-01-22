package com.geekster.project.RestaurantManagementServiceAPI.Service;

import com.geekster.project.RestaurantManagementServiceAPI.DTO.SignUpOutput;
import com.geekster.project.RestaurantManagementServiceAPI.Model.Visitor;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IVisitorRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private IVisitorRepo visitorRepo;

    // Register new visitor in the system
    public SignUpOutput signUpVisitor(Visitor visitor) {
        visitor.setCreatedAt(LocalDateTime.now());
        visitorRepo.save(visitor);
        return new SignUpOutput("success", "Visitor registered successfully");
    }

    // Get all visitors from database
    public List<Visitor> getAllVisitors() {
        return visitorRepo.findAll();
    }

    public Visitor getVisitorById(Integer visitorId) {
        // Find specific visitor by ID
        return visitorRepo.findById(visitorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Visitor not found with ID: " + visitorId));
    }

    public void updateVisitor(Integer visitorId, Visitor visitor) {
        Visitor existingVisitor = getVisitorById(visitorId);

        existingVisitor.setVisitorName(visitor.getVisitorName());
        existingVisitor.setVisitorEmail(visitor.getVisitorEmail());
        existingVisitor.setVisitorType(visitor.getVisitorType());

        visitorRepo.save(existingVisitor);
    }

    public void deleteVisitor(Integer visitorId) {
        Visitor visitor = getVisitorById(visitorId);
        visitorRepo.delete(visitor);
    }
}
