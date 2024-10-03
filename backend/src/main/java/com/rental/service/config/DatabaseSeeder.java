package com.rental.service.config;

import com.rental.service.entities.Admin;
import com.rental.service.enums.Role;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.AdminService;
import com.rental.service.services.exceptions.ExistingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Value("${api.security.admin.name}")
    private String name;

    @Value("${api.security.admin.email}")
    private String email;

    @Value("${api.security.admin.secret}")
    private String password;

    private final UserRepository userRepository;
    private final AdminService adminService;

    @Autowired
    public DatabaseSeeder(UserRepository userRepository, AdminService adminService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
    }

    @PostConstruct
    public void seedDatabase() {
        adminUser();
    }

    private void adminUser() throws ExistingUserException {
        var existingAdmin = userRepository.existsByEmail(email);

        if (!existingAdmin) {
            var admin = Admin.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(this.password)
                    .role(Role.ADMIN)
                    .build();

            adminService.create(admin);
        }
    }
}
