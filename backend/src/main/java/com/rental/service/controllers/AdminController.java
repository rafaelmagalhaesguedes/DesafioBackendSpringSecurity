package com.rental.service.controllers;

import com.rental.service.controllers.dto.admin.AdminRequest;
import com.rental.service.controllers.dto.admin.AdminResponse;
import com.rental.service.controllers.dto.admin.AdminUpdateRequest;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new admin")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Admin created successfully"),
                            @ApiResponse(responseCode = "409", description = "Admin with the given email already exists") })
    public AdminResponse createAdmin(@RequestBody @Valid AdminRequest adminRequest) throws ExistingUserException {
        var newAdmin = adminService.create(adminRequest.toAdmin());
        return AdminResponse.fromAdmin(newAdmin);
    }

    @GetMapping("/{adminId}")
    @Operation(summary = "Get an admin by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Admin found"),
                            @ApiResponse(responseCode = "404", description = "Admin not found") })
    public AdminResponse findAdminById(@PathVariable Long adminId) throws AdminNotFoundException {
        var admin = adminService.findById(adminId);
        return AdminResponse.fromAdmin(admin);
    }

    @PutMapping("/{adminId}")
    @Operation(summary = "Update an existing admin")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Admin updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Admin not found") })
    public ResponseEntity<Void> updateAdmin(@PathVariable Long adminId, @RequestBody @Valid AdminUpdateRequest adminRequest) throws AdminNotFoundException {
        adminService.update(adminRequest.toAdmin(), adminId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adminId}/status")
    @Operation(summary = "Update the status of an admin")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Admin status updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Admin not found") })
    public ResponseEntity<Void> updateStatusAdmin(@PathVariable Long adminId, @RequestParam boolean isActive) throws AdminNotFoundException {
        adminService.updateActiveStatus(adminId, isActive);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{adminId}")
    @Operation(summary = "Soft delete an admin by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Admin deleted successfully"),
                            @ApiResponse(responseCode = "404", description = "Admin not found") })
    public ResponseEntity<Void> softDeleteAdmin(@PathVariable Long adminId) throws AdminNotFoundException {
        adminService.softDelete(adminId);
        return ResponseEntity.noContent().build();
    }
}