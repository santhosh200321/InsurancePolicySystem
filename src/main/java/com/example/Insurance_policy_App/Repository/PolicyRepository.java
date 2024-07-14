package com.example.Insurance_policy_App.Repository;

import com.example.Insurance_policy_App.Entity.PolicyAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyAgent,Long> {

}
