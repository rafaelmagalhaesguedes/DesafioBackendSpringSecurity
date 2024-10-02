package com.rental.service.controllers;

import com.rental.service.controllers.dto.admin.AdminRequest;
import com.rental.service.controllers.dto.admin.AdminResponse;
import com.rental.service.services.AdminService;
import com.rental.service.services.exceptions.AdminNotFoundException;
import com.rental.service.services.exceptions.ExistingUserException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse create(@RequestBody @Valid AdminRequest adminRequest) throws ExistingUserException {
        var newAdmin = adminService.create(adminRequest.toAdmin());
        return AdminResponse.fromAdmin(newAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid AdminRequest adminRequest) throws AdminNotFoundException {
        adminService.update(adminRequest.toAdmin(), id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam boolean isActive) throws AdminNotFoundException {
        adminService.updateStatus(id, isActive);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) throws AdminNotFoundException {
        adminService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public AdminResponse findById(@PathVariable Long id) throws AdminNotFoundException {
        var admin = adminService.findById(id);
        return AdminResponse.fromAdmin(admin);
    }
}
