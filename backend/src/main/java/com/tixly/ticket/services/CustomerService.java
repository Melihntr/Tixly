package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tixly.ticket.entity.Customer;


@Service
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthService authService;

    public void createCustomer(Customer customer) {
        // Save the customer to the database
        String insertSql = "INSERT INTO customer (username, password, mail, gender) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, customer.getUsername(), customer.getPassword(), customer.getmail(), customer.getGender());

        // Retrieve the customer ID
        String selectSql = "SELECT id FROM customer WHERE username = ?";
        Long customerId = jdbcTemplate.queryForObject(selectSql, new Object[]{customer.getUsername()}, Long.class);

        // Generate auth_key and update it for the customer
        String authKey = authService.generateAuthKey(customer.getUsername());
        authService.updateAuthKeyForCustomer(customerId, authKey);
    }
    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (username, password, mail, gender, auth_key) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getUsername(), customer.getPassword(),
                customer.getmail(), customer.getGender(), customer.getAuthKey());
    }

   @Transactional
    public boolean deleteCustomerById(Long customerId) {
        // Check if the customer exists
        String selectSql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(selectSql, new Object[]{customerId}, Integer.class);

        if (count != null && count > 0) {
            // Delete the customer
            String deleteSql = "DELETE FROM customer WHERE id = ?";
            jdbcTemplate.update(deleteSql, customerId);
            return true;
        } else {
            return false;
        }
    }
}


