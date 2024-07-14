package com.example.Insurance_policy_App.Service;

import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import com.example.Insurance_policy_App.Exception.InsuranceNotFoundException;
import com.example.Insurance_policy_App.Repository.InsurancePolicyRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsurancePolicyServiceTest {

    @InjectMocks
    private InsurancePolicyService service;
    @Mock
    private InsurancePolicyRepository repository;

    private InsurancePolicyProvider HealthPolicy;
    private InsurancePolicyProvider EducationPolicy;

    @BeforeEach
    void init() {
        HealthPolicy = InsurancePolicyProvider.builder()
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
        EducationPolicy = InsurancePolicyProvider.builder()
                .id(1L)
                .policyName("EducationPolicy")
                .policyType("15Years")
                .available(true)
                .monthlyPremium(15000L)
                .description("ONE POLICY THAT CAN CHANE LIFE")
                .startDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .endDate(LocalDate.ofEpochDay(2036 - 12 - 12))
                .expiryDate(LocalDate.ofEpochDay(2036 - 12 - 12))
                .build();
    }

    @Test
    public void savePolicies()
    {
        when(repository.save(any(InsurancePolicyProvider.class))).thenReturn(HealthPolicy);
        InsurancePolicyProvider test = service.createInsurancePolicy(HealthPolicy);
        assertNotNull(test);
        assertEquals("HealthInsurancePolicy",test.getPolicyName());
    }
    @Test
    public void getAllPolicies()
    {
        List<InsurancePolicyProvider> list = new ArrayList<>();
        list.add(HealthPolicy);
        list.add(EducationPolicy);
        when(repository.findAll()).thenReturn(list);
        List<InsurancePolicyProvider> test = service.getAllInsurancePolicies();
        assertEquals(2,test.size());
        assertNotNull(test);
    }
    @Test
    public void updateUser() throws InsuranceNotFoundException {
        when(repository.save(any(InsurancePolicyProvider.class))).thenReturn(HealthPolicy);
        when(repository.findById(anyLong())).thenReturn(Optional.of(HealthPolicy));
        InsurancePolicyProvider test = service.updateInsurancePolicy(1L,HealthPolicy);
        test.setPolicyName("SuperHealthPolicy");
        InsurancePolicyProvider savedTest = service.updateInsurancePolicy(test.getId(),HealthPolicy);
        assertEquals("SuperHealthPolicy",savedTest.getPolicyName());
    }
    @Test
    public void getById() throws InsuranceNotFoundException {
        when(repository.findById(anyLong())).thenReturn(Optional.of(HealthPolicy));
        InsurancePolicyProvider test = service.getInsurancePolicyById(1L);
        assertEquals("HealthInsurancePolicy",test.getPolicyName());
    }
    @Test
    public void deleteById() throws InsuranceNotFoundException
    {
        when(repository.findById(1L)).thenReturn(Optional.of(HealthPolicy));
        doNothing().when(repository).delete(HealthPolicy);
        service.deleteInsurancePolicy(1L);
        verify(repository, times(1)).findById(1L);
    }
}