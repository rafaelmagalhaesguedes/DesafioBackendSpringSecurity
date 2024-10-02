package com.rental.service.controllers;

import com.rental.service.controllers.dto.CustomerRequest;
import com.rental.service.controllers.dto.CustomerResponse;
import com.rental.service.entities.Customer;
import com.rental.service.entities.User;
import com.rental.service.services.CustomerService;
import com.rental.service.services.exceptions.ExistingCustomerException;
import com.rental.service.services.exceptions.CustomerNotFoundException;
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
    public CustomerResponse saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) throws ExistingCustomerException {
        var customer = customerService.create(customerRequest.toCustomer());
        return CustomerResponse.fromCustomer(customer);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        var customer = customerService.findById(customerId);
        return CustomerResponse.fromCustomer(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerId, @RequestBody @Valid CustomerRequest customerRequest) throws CustomerNotFoundException {
        customerService.update(customerId, customerRequest.toCustomer());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CustomerResponse> findAllCustomers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        List<Customer> customerList = customerService.list(pageNumber, pageSize);

        return  customerList.stream()
                .map(CustomerResponse::fromCustomer)
                .collect(Collectors.toList());
    }
}
