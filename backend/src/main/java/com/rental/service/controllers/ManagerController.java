package com.rental.service.controllers;

import com.rental.service.controllers.dto.manager.ManagerRequest;
import com.rental.service.controllers.dto.manager.ManagerResponse;
import com.rental.service.controllers.dto.manager.ManagerUpdateRequest;
import com.rental.service.services.ManagerService;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.ManagerNotFoundException;
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
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new manager")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Manager created successfully"),
                            @ApiResponse(responseCode = "409", description = "Manager with the given email already exists") })
    public ManagerResponse createManager(@RequestBody @Valid ManagerRequest request) throws ExistingUserException {
        var newManager = managerService.create(request.toManager());

        return ManagerResponse.fromManager(newManager);
    }

    @GetMapping("/{managerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get a manager by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Manager found"),
                            @ApiResponse(responseCode = "404", description = "Manager not found") })
    public ManagerResponse findManagerById(@PathVariable Long managerId) throws ManagerNotFoundException {
        var manager = managerService.findById(managerId);

        return ManagerResponse.fromManager(manager);
    }

    @PutMapping("/{managerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @Operation(summary = "Update manager by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Manager updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Manager not found") })
    public ResponseEntity<Void> updateManager(@PathVariable Long managerId,
                                              @RequestBody ManagerUpdateRequest request) throws ManagerNotFoundException {
        managerService.update(managerId, request.toManager());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{managerId}/active-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update the active status of an manager")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Manager active status updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Manager not found") })
    public ResponseEntity<Void> updateActiveStatus(@PathVariable Long managerId, @RequestParam boolean isActive) throws ManagerNotFoundException {
        managerService.updateActiveStatus(managerId, isActive);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{managerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Soft delete an manager by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Manager deleted successfully"),
                            @ApiResponse(responseCode = "404", description = "Manager not found") })
    public ResponseEntity<Void> softDelete(@PathVariable Long managerId) throws ManagerNotFoundException {
        managerService.softDelete(managerId);
        return ResponseEntity.noContent().build();
    }
}
