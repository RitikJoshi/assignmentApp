package com.assignment6.assignmentApp.repository;

import com.assignment6.assignmentApp.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository {
    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID uuid);

    Customer createCustomer(Customer customer);

    boolean updateCustomer(UUID uuid, Customer customer);

    boolean deleteCustomer(UUID uuid);
}
