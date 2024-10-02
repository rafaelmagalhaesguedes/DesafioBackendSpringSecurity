package com.rental.service.services;

import com.rental.service.entities.Customer;
import com.rental.service.repositories.CustomerRepository;
import com.rental.service.repositories.UserRepository;
import com.rental.service.services.exceptions.ExistingCustomerException;
import com.rental.service.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Customer create(Customer customer) throws ExistingCustomerException {
        var existingCustomer = userRepository.findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {
            throw new ExistingCustomerException();
        }

        var encodedPassword = passwordEncoder(customer.getPassword());

        customer.setPassword(encodedPassword);

        return customerRepository.save(customer);
    }

    public Customer findById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    public void update(Long customerId, Customer customer) throws CustomerNotFoundException {
        var customerFromDb = findById(customerId);

        customerFromDb.setName(customer.getName());
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setNumberPhone(customer.getNumberPhone());
        customerFromDb.setDocument(customer.getDocument());

        customerRepository.save(customerFromDb);
    }

    @Transactional
    public void delete(Long customerId) throws CustomerNotFoundException {
        var customer = findById(customerId);
        customerRepository.deleteById(customer.getId());
    }

    public List<Customer> list(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);

        return page.getContent();
    }

    private static String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
