package com.rental.service.services;

import com.rental.service.entities.Manager;
import com.rental.service.repositories.ManagerRepository;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.ManagerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerService {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerService(UserRepository userRepository, ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Manager create(Manager manager) throws ExistingUserException {
        var existingManager = userRepository.existsByEmail(manager.getEmail());

        if (existingManager) {
            throw new ExistingUserException();
        }

        var encodedPassword = passwordEncoder.encode(manager.getPassword());

        manager.setPassword(encodedPassword);

        return managerRepository.save(manager);
    }

    public Manager findById(Long managerId) throws ManagerNotFoundException {
        return managerRepository.findById(managerId)
                .orElseThrow(ManagerNotFoundException::new);
    }

    public void update(Long managerId, Manager manager) throws ManagerNotFoundException {
        var managerFromDb = findById(managerId);

        managerFromDb.setName(manager.getName());
        managerFromDb.setEmail(manager.getEmail());
        managerFromDb.setRegister(manager.getRegister());
        managerFromDb.setDepartment(manager.getDepartment());
        managerFromDb.setLocation(manager.getLocation());

        managerRepository.save(managerFromDb);
    }

    public void updateActiveStatus(Long managerId, boolean activeStatus) throws ManagerNotFoundException {
        var manager = findById(managerId);

        manager.setActive(activeStatus);

        managerRepository.save(manager);
    }

    public void softDelete(Long managerId) throws ManagerNotFoundException {
        var manager = findById(managerId);

        manager.setDeleted(true);

        managerRepository.save(manager);
    }
}
