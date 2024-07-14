package com.example.Insurance_policy_App.Service;
import com.example.Insurance_policy_App.Entity.PolicyAgent;
import com.example.Insurance_policy_App.Exception.PolicyNotActiveException;
import com.example.Insurance_policy_App.Exception.PolicyNotFoundException;
import com.example.Insurance_policy_App.Repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PolicyAgentService {

    @Autowired
    private PolicyRepository policyRepository;

    public PolicyAgent createPolicy(PolicyAgent policy) {
        return policyRepository.save(policy);
    }

    public void deletePolicy(Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        policyRepository.delete(policy);
    }
    public PolicyAgent updatePolicy(Long policyId, PolicyAgent policyDetails) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        policy.setStartDate(policyDetails.getStartDate());
        policy.setEndDate(policyDetails.getEndDate());
        policy.setMonthlyPremium(policyDetails.getMonthlyPremium());
        policy.setStatus(policyDetails.getStatus());
        policy.setInsurancePolicy(policyDetails.getInsurancePolicy());
        return policyRepository.save(policy);
    }
    public PolicyAgent updatePolicyExpiringTime(Long policyId, LocalDate newExpiringTime) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        policy.setEndDate(newExpiringTime);
        return policyRepository.save(policy);
    }
    public boolean isPolicyActive(Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        return policy.getStatus() == PolicyAgent.PolicyStatus.ACTIVE;
    }

    public PolicyAgent claimPolicy(Long policyId) throws PolicyNotFoundException,PolicyNotActiveException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        if (policy.getStatus() != PolicyAgent.PolicyStatus.ACTIVE) {
            throw new PolicyNotActiveException("Policy is not active and cannot be claimed");
        }
        if (policy.getEndDate().isBefore(LocalDate.now())) {
            throw new PolicyNotActiveException("Policy has expired and cannot be claimed");
        }
        policy.setStatus(PolicyAgent.PolicyStatus.CLAIMED);
        return policyRepository.save(policy);
    }
    public PolicyAgent getPolicyById(Long policyId) throws PolicyNotFoundException {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
    }
    public double getMonthlyPremium(Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        return policy.getMonthlyPremium();
    }

    public boolean checkAvilability(Long policyId) throws PolicyNotFoundException
    {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        if(policy.getStatus()== PolicyAgent.PolicyStatus.ACTIVE)
        {
            return true;
        }
        return false;
    }
    public PolicyAgent renewPolicy(Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        LocalDate newDate = policy.getEndDate().plusYears(1);
        policy.setStatus(PolicyAgent.PolicyStatus.ACTIVE);
        return  policyRepository.save(policy);
    }
    public void cancelPolicy(Long policyId) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found for this id :: " + policyId));
        policy.setStatus(PolicyAgent.PolicyStatus.CANCELLED);
        policyRepository.delete(policy);
    }
    public PolicyAgent updatePolicyPremium(Long policyId, double newPremium) throws PolicyNotFoundException {
        PolicyAgent policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id " + policyId));
        policy.setMonthlyPremium(newPremium);
        return policyRepository.save(policy);
    }

    }


