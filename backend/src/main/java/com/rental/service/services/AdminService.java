package com.rental.service.services;

import com.rental.service.entities.Admin;
import com.rental.service.repositories.AdminRepository;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.exceptions.AdminNotFoundException;
import com.rental.service.services.exceptions.ExistingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(UserRepository userRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Admin create(Admin admin) {
        var existingAdmin = userRepository.existsByEmail(admin.getEmail());

        if (existingAdmin) {
            throw new ExistingUserException();
        }

        var encodedPassword = passwordEncoder.encode(admin.getPassword());

        admin.setPassword(encodedPassword);

        return adminRepository.save(admin);
    }

    public Admin findById(Long id) throws AdminNotFoundException {
        return adminRepository.findById(id)
                .orElseThrow(AdminNotFoundException::new);
    }

    @Transactional
    public void update(Admin admin, Long id) throws AdminNotFoundException {
        var existingAdmin = findById(id);

        existingAdmin.setName(admin.getName());
        existingAdmin.setEmail(admin.getEmail());

        adminRepository.save(existingAdmin);
    }

    @Transactional
    public void updateActiveStatus(Long id, boolean isActive) throws AdminNotFoundException {
        var existingAdmin = findById(id);
        existingAdmin.setActive(isActive);

        adminRepository.save(existingAdmin);
    }

    @Transactional
    public void softDelete(Long id) throws AdminNotFoundException {
        var existingAdmin = findById(id);
        existingAdmin.setDeleted(true);

        adminRepository.save(existingAdmin);
    }
}
