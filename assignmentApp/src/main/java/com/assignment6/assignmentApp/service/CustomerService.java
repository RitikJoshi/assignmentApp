package com.assignment6.assignmentApp.service;

import com.assignment6.assignmentApp.model.Customer;
import com.assignment6.assignmentApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Implement methods for CRUD operations
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(UUID uuid) {
        return customerRepository.getCustomerById(uuid);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.createCustomer(customer);
    }

    public boolean updateCustomer(UUID uuid, Customer customer) {
        return customerRepository.updateCustomer(uuid, customer);
    }

    public boolean deleteCustomer(UUID uuid) {
        return customerRepository.deleteCustomer(uuid);
    }


}

