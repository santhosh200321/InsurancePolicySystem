package com.example.Insurance_policy_App.Repository;

import com.example.Insurance_policy_App.Entity.InsurancePolicyProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicyProvider,Long>
{

}
