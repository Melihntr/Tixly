package com.tixly.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.services.CustomerService;




@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        // Validate the input
        if (customer.getUsername() == null || customer.getUsername().length() < 4 || customer.getUsername().length() > 50 ||
            customer.getPassword() == null || customer.getPassword().length() < 4 || customer.getPassword().length() > 50) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }

        // Create the customer and generate auth_key
        customerService.createCustomer(customer);

        return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        // Delete the customer
        boolean isDeleted = customerService.deleteCustomerById(id);

        if (isDeleted) {
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }
}