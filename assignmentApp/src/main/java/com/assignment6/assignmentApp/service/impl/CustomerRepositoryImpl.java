package com.assignment6.assignmentApp.service.impl;

import com.assignment6.assignmentApp.model.Customer;
import com.assignment6.assignmentApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate; // Injected JdbcTemplate for database access

    @Autowired
    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Implement the logic to fetch all customers from the database using jdbcTemplate.

        return jdbcTemplate.query("SELECT * FROM customers", (rs, rowNum) -> {
            // Map the result set to a Customer object
            return new Customer(
                    rs.getString("uuid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("street"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("email"),
                    rs.getString("phone")
                    // Map other fields here
            );
        });
    }

    @Override
    public Customer getCustomerById(UUID uuid) {
        // Implement the logic to retrieve a customer by their UUID from the database using jdbcTemplate.
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE uuid = ?",
                new Object[]{uuid.toString()},
                (rs, rowNum) -> {
                    // Map the result set to a Customer object
                    return new Customer(
                            rs.getString("uuid"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("street"),
                            rs.getString("address"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("email"),
                            rs.getString("phone")
                            // Map other fields here
                    );
                }
        );
    }

    @Override
    public Customer createCustomer(Customer customer) {
        // Implement the logic to create a new customer in the database using jdbcTemplate.
        jdbcTemplate.update(
                "INSERT INTO customers (uuid, first_name, last_name, street, address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getStreet(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getEmail(),
                customer.getPhone()
                // Set other parameters here
        );
        return customer; // Return the created customer
    }

    @Override
    public boolean updateCustomer(UUID uuid, Customer customer) {
        // Implement the logic to update a customer's information in the database using jdbcTemplate.
        jdbcTemplate.update(
                "UPDATE customers SET first_name = ?, last_name = ?, street = ?, address = ?, city = ?, state = ?, email = ?, phone = ? WHERE uuid = ?",
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getStreet(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getEmail(),
                customer.getPhone(),
                uuid.toString()
        );
        return true; // Return true if the update was successful
    }

    @Override
    public boolean deleteCustomer(UUID uuid) {
        // Implement the logic to delete a customer by their UUID from the database using jdbcTemplate.
        int deleted = jdbcTemplate.update("DELETE FROM customers WHERE uuid = ?", uuid.toString());
        return deleted > 0; // Return true if a record was deleted
    }
}
