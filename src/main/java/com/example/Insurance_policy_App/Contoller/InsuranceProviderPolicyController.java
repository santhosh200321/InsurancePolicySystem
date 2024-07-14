package com.example.Insurance_policy_App.Contoller;
import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import com.example.Insurance_policy_App.Exception.InsuranceNotFoundException;
import com.example.Insurance_policy_App.Service.InsurancePolicyService;
import com.example.Insurance_policy_App.Service.PolicyAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insurance-provider")
public class InsuranceProviderPolicyController {

    @Autowired
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private PolicyAgentService policyService;

    // Create a new insurance policy
    @PostMapping("/policies")
    public ResponseEntity<InsurancePolicyProvider> createInsurancePolicy(@RequestBody InsurancePolicyProvider insurancePolicy) {
        InsurancePolicyProvider createdPolicy = insurancePolicyService.createInsurancePolicy(insurancePolicy);
        return ResponseEntity.ok(createdPolicy);
    }

    // Update an existing insurance policy
    @PutMapping("/policies/{policyId}")
    public ResponseEntity<InsurancePolicyProvider> updateInsurancePolicy(@PathVariable Long policyId, @RequestBody InsurancePolicyProvider policyDetails) throws InsuranceNotFoundException {
        InsurancePolicyProvider updatedPolicy = insurancePolicyService.updateInsurancePolicy(policyId, policyDetails);
        return ResponseEntity.ok(updatedPolicy);
    }

    // Delete an insurance policy
    @DeleteMapping("/policies/{policyId}")
    public ResponseEntity<Void> deleteInsurancePolicy(@PathVariable Long policyId) throws InsuranceNotFoundException {
        insurancePolicyService.deleteInsurancePolicy(policyId);
        return ResponseEntity.noContent().build();
    }

    // Get all insurance policies
    @GetMapping("/policies")
    public ResponseEntity<List<InsurancePolicyProvider>> getAllInsurancePolicies() {
        List<InsurancePolicyProvider> policies = insurancePolicyService.getAllInsurancePolicies();
        return ResponseEntity.ok(policies);
    }
    // Get an insurance policy by ID
    @GetMapping("/policies/{policyId}")
    public ResponseEntity<InsurancePolicyProvider> getInsurancePolicyById(@PathVariable Long policyId) throws InsuranceNotFoundException {
        InsurancePolicyProvider policy = insurancePolicyService.getInsurancePolicyById(policyId);
        return ResponseEntity.ok(policy);
    }

}
