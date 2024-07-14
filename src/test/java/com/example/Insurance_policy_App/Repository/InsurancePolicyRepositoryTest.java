package com.example.Insurance_policy_App.Repository;

import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class InsurancePolicyRepositoryTest {

    @Autowired
    private InsurancePolicyRepository repository;

    private InsurancePolicyProvider HealthPolicy;
    private InsurancePolicyProvider EducationPolicy;

    @BeforeEach
    void init()
    {
        HealthPolicy = InsurancePolicyProvider.builder()
                .id(1L)
                .policyName("HealthInsurancePolicy")
                .policyType("12Years")
                .available(true)
                .monthlyPremium(12400L)
                .description("ONE POLICY THAT CAN CHANE LIFE")
                .startDate(LocalDate.ofEpochDay(2024-12-12))
                .endDate(LocalDate.ofEpochDay(2036-12-12))
                .expiryDate(LocalDate.ofEpochDay(2036-12-12))
                .build();
        EducationPolicy = InsurancePolicyProvider.builder()
                .id(1L)
                .policyName("EducationPolicy")
                .policyType("12Years")
                .available(true)
                .monthlyPremium(12400L)
                .description("ONE POLICY THAT CAN CHANE LIFE")
                .startDate(LocalDate.ofEpochDay(2024-12-12))
                .endDate(LocalDate.ofEpochDay(2036-12-12))
                .expiryDate(LocalDate.ofEpochDay(2036-12-12))
                .build();
    }
    @Test
    public void savePolicies()
    {
        InsurancePolicyProvider TestPolicy = repository.save(HealthPolicy);
        assertEquals("HealthInsurancePolicy",TestPolicy.getPolicyName());
    }

    @Test
    public void getAllPolicy()
    {
        InsurancePolicyProvider TestPolicy = repository.save(HealthPolicy);
        InsurancePolicyProvider TestPolicy2 = repository.save(EducationPolicy);
        List<InsurancePolicyProvider> list = repository.findAll();
        assertEquals(1,list.size());
    }
    @Test
    public void getById()
    {
        InsurancePolicyProvider TestPolicy = repository.save(EducationPolicy);
        repository.findById(TestPolicy.getId());
        assertEquals("EducationPolicy",TestPolicy.getPolicyName());
    }
    @Test
    public void upadateInsurancePolicy()
    {
        InsurancePolicyProvider TestPolicy = repository.save(EducationPolicy);
        InsurancePolicyProvider updatedPolicy = repository.findById(TestPolicy.getId()).get();
        updatedPolicy.setPolicyType("15yrs");
        repository.save(updatedPolicy);
        assertEquals("EducationPolicy",TestPolicy.getPolicyName());
    }

}