package com.example.Insurance_policy_App.Repository;

import com.example.Insurance_policy_App.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
     List<Customer> findCustomerByName(String name);
    List<Customer> findByStatus(Customer.CustomerStatus status);
}
