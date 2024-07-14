package com.example.Insurance_policy_App.Contoller;

import com.example.Insurance_policy_App.Entity.Customer;
import com.example.Insurance_policy_App.Entity.PolicyAgent;
import com.example.Insurance_policy_App.Exception.CustomerNotFoundException;
import com.example.Insurance_policy_App.Exception.PolicyNotActiveException;
import com.example.Insurance_policy_App.Exception.PolicyNotFoundException;
import com.example.Insurance_policy_App.Service.CustomerManagementService;
import com.example.Insurance_policy_App.Service.PolicyAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/customers")
public class CustomerPolicyController {

    @Autowired
    private CustomerManagementService customerService;
    @Autowired
    private PolicyAgentService policyService;

    // Add a new policy for a customer
    @PostMapping("/{customerId}/policies")
    public ResponseEntity<PolicyAgent> addPolicy(@PathVariable Long customerId, @RequestBody PolicyAgent policy) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        policy.setCustomer(customer);
        PolicyAgent createdPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(createdPolicy);
    }
    // Delete a policy
    @DeleteMapping("/policiesDelete/{policyId}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long policyId) throws PolicyNotFoundException {
        policyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }
    // Update a policy
    @PutMapping("/policies/{policyId}")
    public ResponseEntity<PolicyAgent> updatePolicy(@PathVariable Long policyId, @RequestBody PolicyAgent policyDetails) throws PolicyNotFoundException {
        PolicyAgent updatedPolicy = policyService.updatePolicy(policyId, policyDetails);
        return ResponseEntity.ok(updatedPolicy);
    }

    // Renew a policy (extend the end date)
    @PutMapping("/policies/{policyId}/renew")
    public ResponseEntity<PolicyAgent> renewPolicy(@PathVariable Long policyId, @RequestParam(value="Date")LocalDate newEndDate) throws PolicyNotFoundException {
        PolicyAgent updatedPolicy = policyService.updatePolicyExpiringTime(policyId, newEndDate);
        return ResponseEntity.ok(updatedPolicy);
    }

    // Check if a policy is available (active)
    @GetMapping("/policies/{policyId}/is-active")
    public ResponseEntity<Boolean> isPolicyActive(@PathVariable Long policyId) throws PolicyNotFoundException {
        boolean isActive = policyService.isPolicyActive(policyId);
        return ResponseEntity.ok(isActive);
    }
    // Claim a policy
    @PutMapping("/policies/{policyId}/claim")
    public ResponseEntity<PolicyAgent> claimPolicy(@PathVariable Long policyId) throws PolicyNotFoundException, PolicyNotActiveException {
        PolicyAgent claimedPolicy = policyService.claimPolicy(policyId);
        return ResponseEntity.ok(claimedPolicy);
    }
    // Get monthly premium for a policy
    @GetMapping("/policies/{policyId}/monthly-premium")
    public ResponseEntity<Double> getMonthlyPremium(@PathVariable Long policyId) throws PolicyNotFoundException {
        double monthlyPremium = policyService.getMonthlyPremium(policyId);
        return ResponseEntity.ok(monthlyPremium);
    }

    // Get a policy by ID
    @GetMapping("/policies/{policyId}")
    public ResponseEntity<PolicyAgent> getPolicyById(@PathVariable Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyService.getPolicyById(policyId);
        return ResponseEntity.ok(policy);
    }
    // cancel policy by ID
    @DeleteMapping("/policies/{policyId}")
    public ResponseEntity<Void> cancelPolicy(@PathVariable Long policyId) throws PolicyNotFoundException {
        policyService.cancelPolicy(policyId);
        return ResponseEntity.noContent().build();
    }
    // renew policy By ID
    @PutMapping
    public ResponseEntity<PolicyAgent> renewPolicy(@PathVariable Long policyId) throws PolicyNotFoundException, PolicyNotActiveException {
        PolicyAgent claimedPolicy = policyService.renewPolicy(policyId);
        return ResponseEntity.ok(claimedPolicy);
    }
}

