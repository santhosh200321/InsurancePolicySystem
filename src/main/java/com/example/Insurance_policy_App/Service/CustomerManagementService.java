package com.example.Insurance_policy_App.Service;

import com.example.Insurance_policy_App.Entity.Customer;
import com.example.Insurance_policy_App.Entity.PolicyAgent;
import com.example.Insurance_policy_App.Exception.CustomerNotFoundException;
import com.example.Insurance_policy_App.Exception.PolicyNotFoundException;
import com.example.Insurance_policy_App.Repository.CustomerRepository;
import com.example.Insurance_policy_App.Repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManagementService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PolicyRepository policyRepository;

    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this id :: " + customerId));
    }
    public List<Customer> getCustomerByName(String name)
    {
       return customerRepository.findCustomerByName(name);
    }
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer Customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for this id :: " + customerId));
    }

    public Customer saveCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomerByPolicyId(Long policyId) throws PolicyNotFoundException {
        PolicyAgent Policyagent = policyRepository.findById(policyId).orElseThrow(()-> new PolicyNotFoundException("Not Found"));
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getPolicies().contains(Policyagent))
                .collect(Collectors.toList());
    }
    public Customer updateCustomerStatusBasedOnPolicy(Long customerId, Long policyId) throws PolicyNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new PolicyNotFoundException("Customer not found with id " + customerId));

        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id " + policyId));

        // Example logic: If policy is active, set customer status to ACTIVE
        if (policy.getStatus() == PolicyAgent.PolicyStatus.ACTIVE) {
            customer.setStatus(Customer.CustomerStatus.ACTIVE);
        } else {
            customer.setStatus(Customer.CustomerStatus.INACTIVE);
        }
        return customerRepository.save(customer);
    }
    public Customer deactivateCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));
        customer.setStatus(Customer.CustomerStatus.INACTIVE);
        return customerRepository.save(customer);
    }
    public Customer reactivateCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + customerId));
        customer.setStatus(Customer.CustomerStatus.ACTIVE);
        return customerRepository.save(customer);
    }
    public List<Customer> getActiveCustomers() {
        return customerRepository.findByStatus(Customer.CustomerStatus.ACTIVE);
    }
    public List<Customer> getInactiveCustomers() {
        return customerRepository.findByStatus(Customer.CustomerStatus.INACTIVE);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
