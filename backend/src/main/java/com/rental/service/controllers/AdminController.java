package com.rental.service.controllers;

import com.rental.service.controllers.dto.admin.AdminRequest;
import com.rental.service.controllers.dto.admin.AdminResponse;
import com.rental.service.services.AdminService;
import com.rental.service.services.exceptions.AdminNotFoundException;
import com.rental.service.services.exceptions.ExistingUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin created successfully"),
            @ApiResponse(responseCode = "409", description = "Admin with the given email already exists")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminResponse create(@RequestBody @Valid AdminRequest adminRequest) throws ExistingUserException {
        var newAdmin = adminService.create(adminRequest.toAdmin());
        return AdminResponse.fromAdmin(newAdmin);
    }

    @Operation(summary = "Update an existing admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin updated successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid AdminRequest adminRequest) throws AdminNotFoundException {
        adminService.update(adminRequest.toAdmin(), id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update the status of an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Admin status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam boolean isActive) throws AdminNotFoundException {
        adminService.updateStatus(id, isActive);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Soft delete an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Admin soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) throws AdminNotFoundException {
        adminService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find an admin by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin found"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @GetMapping("/{id}")
    public AdminResponse findById(@PathVariable Long id) throws AdminNotFoundException {
        var admin = adminService.findById(id);
        return AdminResponse.fromAdmin(admin);
    }
}