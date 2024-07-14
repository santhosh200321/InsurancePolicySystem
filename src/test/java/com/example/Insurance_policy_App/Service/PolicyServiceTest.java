package com.example.Insurance_policy_App.Service;

import com.example.Insurance_policy_App.Entity.Customer;
import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import com.example.Insurance_policy_App.Entity.PolicyAgent;
import com.example.Insurance_policy_App.Exception.PolicyNotActiveException;
import com.example.Insurance_policy_App.Exception.PolicyNotFoundException;
import com.example.Insurance_policy_App.Repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @InjectMocks
    private PolicyAgentService policyService;
    @Mock
    private PolicyRepository policyRepository;

    private PolicyAgent policy;
    private Customer customer1;
    private InsurancePolicyProvider insurancePolicy;
    @BeforeEach
    public void setUp() {
        customer1 = Customer.builder()
                .name("Customer1")
                .email("Customer123@gamil.com")
                .password("1234567")
                .username("CustomerXxx56")
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
    public void testCreatePolicy() {
        when(policyRepository.save(any(PolicyAgent.class))).thenReturn(policy);
        PolicyAgent createdPolicy = policyService.createPolicy(policy);
        assertNotNull(createdPolicy);
        assertEquals(100.0, createdPolicy.getMonthlyPremium());
        verify(policyRepository, times(1)).save(policy);
    }
    @Test
    public void testGetPolicyById() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        PolicyAgent foundPolicy = policyService.getPolicyById(1L);
        assertNotNull(foundPolicy);
        assertEquals("HealthInsurancePolicy", foundPolicy.getInsurancePolicy().getPolicyName());
        verify(policyRepository, times(1)).findById(1L);
    }
    @Test
    public void testClaimPolicy() throws PolicyNotFoundException, PolicyNotActiveException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(policyRepository.save(any(PolicyAgent.class))).thenReturn(policy);
        PolicyAgent foundPolicy = policyService.claimPolicy(insurancePolicy.getId());
        assertEquals("HealthInsurancePolicy", foundPolicy.getInsurancePolicy().getPolicyName());
    }

    @Test
    public void testMontlyPremium() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
       double updatePremium =  policyService.getMonthlyPremium(insurancePolicy.getId());
       assertEquals(12400L, insurancePolicy.getMonthlyPremium());
    }
    @Test
    public void isActiveTest() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        boolean isActive = policyService.isPolicyActive(insurancePolicy.getId());
        assertEquals(12400L, insurancePolicy.getMonthlyPremium());
        assertEquals(insurancePolicy.isAvailable(), true);
    }
    @Test
    public void UpadatePolicyExpiredTime() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(policyRepository.save(any(PolicyAgent.class))).thenReturn(policy);
        PolicyAgent updated = policyService.
                updatePolicyExpiringTime(insurancePolicy.getId(), insurancePolicy.getEndDate());
        updated.setEndDate(LocalDate.ofEpochDay(2046 - 12 - 21));
        assertEquals(12400L, insurancePolicy.getMonthlyPremium());
        assertThat(2046 - 12 - 21).isNotEqualTo(insurancePolicy.getEndDate());
    }
    @Test
    public void testDeletePolicy() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        doNothing().when(policyRepository).delete(policy);
        policyService.deletePolicy(1L);
        verify(policyRepository, times(1)).findById(1L);
        verify(policyRepository, times(1)).delete(policy);
    }
    @Test
    public void testCheckAvilability() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        boolean checkAvailable = policyService.checkAvilability(insurancePolicy.getId());
        assertTrue(checkAvailable);
    }
    @Test
    public void testRenewal() throws PolicyNotFoundException {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));
        when(policyRepository.save(any(PolicyAgent.class))).thenReturn(policy);
        PolicyAgent newPolicy = policyService.renewPolicy(insurancePolicy.getId());
        assertNotNull(newPolicy);
        assertEquals(PolicyAgent.PolicyStatus.ACTIVE,newPolicy.getStatus());
    }
    }
