package com.example.Insurance_policy_App.Service;

import com.example.Insurance_policy_App.Entity.Customer;
import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import com.example.Insurance_policy_App.Entity.PolicyAgent;
import com.example.Insurance_policy_App.Exception.CustomerNotFoundException;
import com.example.Insurance_policy_App.Exception.PolicyNotFoundException;
import com.example.Insurance_policy_App.Repository.CustomerRepository;
import com.example.Insurance_policy_App.Repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerManagementServiceTest {

    @InjectMocks
    private CustomerManagementService service;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private PolicyAgentService policyAgentService;
    @Mock
    private PolicyRepository policyRepository;

    private  Customer customer1;
    private  InsurancePolicyProvider insurancePolicy;
    private PolicyAgent policy;

    @BeforeEach
    void init()
    {
        customer1 = Customer.builder()
                .name("Customer1")
                .id(1L)
                .email("Customer123@gamil.com")
                .password("1234567")
                .username("CustomerXxx56")
                .status(Customer.CustomerStatus.ACTIVE)
                .build();
        insurancePolicy = InsurancePolicyProvider.builder()
                .id(1L)
                .policyName("HealthInsurancePolicy")
                .policyType("12Years")
                .available(true)
                .monthlyPremium(12400L)
                .description("ONE POLICY THAT CAN CHANE LIFE")
                .startDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .endDate(LocalDate.ofEpochDay(2036 - 12 - 12))
                .expiryDate(LocalDate.ofEpochDay(2036 - 12 - 12))
                .build();
        policy = new PolicyAgent();
        policy.setId(1L);
        policy.setCustomer(customer1);
        policy.setInsurancePolicy(insurancePolicy);
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        policy.setMonthlyPremium(100.0);
        policy.setStatus(PolicyAgent.PolicyStatus.ACTIVE);

    }
    @Test
    public void saveCustomer()
    {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        Customer  customer = service.saveCustomer(customer1);
        assertEquals("Customer1", customer1.getName());
    }
    @Test
    public void  UpdateCustomerByPolicyId() throws PolicyNotFoundException {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        Customer updateCustomerByPolicy = service.updateCustomerStatusBasedOnPolicy(customer1.getId(),policy.getId());
        updateCustomerByPolicy.setStatus(Customer.CustomerStatus.INACTIVE);
        assertEquals("Customer1", customer1.getName());
    }
    @Test
    public void getListOfActiveCustomer()
    {
        List<Customer> list = new ArrayList<>();
        list.add(customer1);
        when(customerRepository.findByStatus(Customer.CustomerStatus.ACTIVE)).thenReturn(list);
        List<Customer> listOfActiveCustomers = service.getActiveCustomers();
        assertEquals(1,listOfActiveCustomers.size());
    }
    @Test
    public void getListOfInactiveCustomer()
    {
        List<Customer> list = new ArrayList<>();
        list.add(customer1);
        when(customerRepository.findByStatus(Customer.CustomerStatus.INACTIVE)).thenReturn(list);
        List<Customer> listOfActiveCustomers = service.getInactiveCustomers();
        assertEquals(1,listOfActiveCustomers.size());
    }
    @Test
    public void setCustomerDeActive() throws CustomerNotFoundException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        Customer customer = service.deactivateCustomer(customer1.getId());
        assertThat(Customer.CustomerStatus.INACTIVE).isEqualTo(customer.getStatus());
    }
    @Test
    public void setCustomerActive() throws CustomerNotFoundException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        Customer customer = service.reactivateCustomer(customer1.getId());
        assertThat(Customer.CustomerStatus.ACTIVE).isEqualTo(customer.getStatus());
    }







}