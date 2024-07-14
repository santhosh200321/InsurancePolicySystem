package com.example.Insurance_policy_App.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InsurancePolicyProvider
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Policy name is mandatory")
    private String policyName;
    @NotBlank(message = "Policy type is mandatory")
    private String policyType;
    @NotNull(message = "Policy amount is mandatory")
    @Min(value = 0, message = "Policy amount should be a positive value")
    private Long policyAmount;
    @Min(value = 0, message = "Monthly premium should be a positive value")
    private Long monthlyPremium;
    @NotNull(message = "Policy start date is mandatory")
    private LocalDate startDate;
    @NotNull(message = "Policy end date is mandatory")
    private LocalDate endDate;
    private boolean available;
    private LocalDate expiryDate;
    private String description;
}
