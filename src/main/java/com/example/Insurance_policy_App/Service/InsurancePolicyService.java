package com.example.Insurance_policy_App.Service;

import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import com.example.Insurance_policy_App.Exception.InsuranceNotFoundException;
import com.example.Insurance_policy_App.Repository.InsurancePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsurancePolicyService {

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    public InsurancePolicyProvider createInsurancePolicy(InsurancePolicyProvider insurancePolicy) {
        return insurancePolicyRepository.save(insurancePolicy);
    }
    public InsurancePolicyProvider updateInsurancePolicy(Long policyId, InsurancePolicyProvider policyDetails) throws InsuranceNotFoundException {
        InsurancePolicyProvider insurancePolicy = insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new InsuranceNotFoundException("Insurance Policy not found for this id :: " + policyId));
        insurancePolicy.setPolicyName(policyDetails.getPolicyName());
        insurancePolicy.setDescription(policyDetails.getDescription());
        insurancePolicy.setPolicyType(policyDetails.getPolicyType());
        insurancePolicy.setPolicyAmount(policyDetails.getPolicyAmount());
        insurancePolicy.setMonthlyPremium(policyDetails.getMonthlyPremium());
        insurancePolicy.setStartDate(policyDetails.getStartDate());
        insurancePolicy.setEndDate(policyDetails.getEndDate());
        insurancePolicy.setExpiryDate(policyDetails.getExpiryDate());
        return insurancePolicyRepository.save(insurancePolicy);
    }
    public void deleteInsurancePolicy(Long policyId) throws InsuranceNotFoundException {
        InsurancePolicyProvider insurancePolicy = insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new InsuranceNotFoundException("Insurance Policy not found for this id :: " + policyId));
        insurancePolicyRepository.delete(insurancePolicy);
    }
    public List<InsurancePolicyProvider> getAllInsurancePolicies() {
        return insurancePolicyRepository.findAll();
    }

    public InsurancePolicyProvider getInsurancePolicyById(Long policyId) throws InsuranceNotFoundException {
        return insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new InsuranceNotFoundException("Insurance Policy not found for this id :: " + policyId));
    }

}
