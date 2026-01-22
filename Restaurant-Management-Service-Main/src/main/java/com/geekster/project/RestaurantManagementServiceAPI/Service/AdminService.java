package com.geekster.project.RestaurantManagementServiceAPI.Service;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Admin;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private IAdminRepo adminRepo;

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Optional<Admin> getAdminById(Integer adminId) {
        return adminRepo.findById(adminId);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public void deleteAdmin(Integer adminId) {
        adminRepo.deleteById(adminId);
    }

    public Admin updateAdmin(Admin admin) {
        return adminRepo.save(admin);
    }
}
