package com.rental.service.services;

import com.rental.service.entities.Customer;
import com.rental.service.repositories.CustomerRepository;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Customer create(Customer customer) throws ExistingUserException {
        var existingCustomer = customerRepository.existsByEmail(customer.getEmail());

        if (existingCustomer) {
            throw new ExistingUserException();
        }

        var encodedPassword = passwordEncoder.encode(customer.getPassword());

        customer.setPassword(encodedPassword);

        return customerRepository.save(customer);
    }

    @Transactional
    public void update(Long customerId, Customer customer) throws CustomerNotFoundException {
        var customerFromDb = findById(customerId);

        customerFromDb.setName(customer.getName());
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setRawNumberPhone(customer.getRawNumberPhone());
        customerFromDb.setRawDocument(customer.getRawDocument());

        customerRepository.save(customerFromDb);
    }

    @Transactional
    public void delete(Long customerId) throws CustomerNotFoundException {
        var customer = findById(customerId);
        customerRepository.deleteById(customer.getId());
    }

    public Customer findById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> list(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);

        return page.getContent();
    }
}
