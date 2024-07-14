package com.example.Insurance_policy_App.Contoller;
import com.example.Insurance_policy_App.Entity.Customer;
import com.example.Insurance_policy_App.Exception.CustomerNotFoundException;
import com.example.Insurance_policy_App.Service.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerManagementController {
    @Autowired
    private CustomerManagementService customerService;
    // Endpoint to get a list of all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Endpoint to search for customers by name
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam String name) {
        List<Customer> customers = customerService.getCustomerByName(name);
        return ResponseEntity.ok(customers);
    }

    // Endpoint to deactivate a customer
    @PutMapping("/{customerId}/deactivate")
    public ResponseEntity<Customer> deactivateCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        Customer deactivatedCustomer = customerService.deactivateCustomer(customerId);
        return ResponseEntity.ok(deactivatedCustomer);
    }

    // Endpoint to reactivate a customer
    @PutMapping("/{customerId}/reactivate")
    public ResponseEntity<Customer> reactivateCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        Customer reactivatedCustomer = customerService.reactivateCustomer(customerId);
        return ResponseEntity.ok(reactivatedCustomer);
    }

    // Endpoint to get active customers
    @GetMapping("/active")
    public ResponseEntity<List<Customer>> getActiveCustomers() {
        List<Customer> customers = customerService.getActiveCustomers();
        return ResponseEntity.ok(customers);
    }
    // Endpoint to get inactive customers
    @GetMapping("/inactive")
    public ResponseEntity<List<Customer>> getInactiveCustomers() {
        List<Customer> customers = customerService.getInactiveCustomers();
        return ResponseEntity.ok(customers);
    }
}
