package com.rental.service.config;

import com.rental.service.entities.Admin;
import com.rental.service.enums.Role;
import com.rental.service.services.AdminService;
import com.rental.service.services.exceptions.ExistingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${api.security.admin.email}")
    private String email;

    @Value("${api.security.admin.secret}")
    public String password;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws ExistingUserException {
        if (!adminService.existsByEmail(email)) {
            var admin = Admin.builder()
                    .name("General Admin")
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.ADMIN)
                    .build();
            adminService.create(admin);
        }
    }
}