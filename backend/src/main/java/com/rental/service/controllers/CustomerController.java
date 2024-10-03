package com.rental.service.controllers;

import com.rental.service.controllers.dto.customer.CustomerRequest;
import com.rental.service.controllers.dto.customer.CustomerResponse;
import com.rental.service.controllers.dto.customer.CustomerUpdateRequest;
import com.rental.service.entities.Customer;
import com.rental.service.services.CustomerService;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Customer created successfully"),
                            @ApiResponse(responseCode = "409", description = "Customer with the given email already exists") })
    public CustomerResponse saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) throws ExistingUserException {
        var customer = customerService.create(customerRequest.toCustomer());
        return CustomerResponse.fromCustomer(customer);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get a customer by ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Customer found"),
                            @ApiResponse(responseCode = "404", description = "Customer not found") })
    public CustomerResponse getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        var customer = customerService.findById(customerId);
        return CustomerResponse.fromCustomer(customer);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update an existing customer")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Customer not found") })
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerId,
                                               @RequestBody @Valid CustomerUpdateRequest customerRequest) throws CustomerNotFoundException {
        customerService.update(customerId, customerRequest.toCustomer());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found") })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get a list of all customers")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully") })
    public List<CustomerResponse> findAllCustomers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        List<Customer> customerList = customerService.list(pageNumber, pageSize);

        return customerList.stream()
                .map(CustomerResponse::fromCustomer)
                .collect(Collectors.toList());
    }
}
