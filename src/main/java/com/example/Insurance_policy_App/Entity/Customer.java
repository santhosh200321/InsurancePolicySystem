package com.example.Insurance_policy_App.Entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username should be between 3 and 50 characters")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PolicyAgent> policies;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    public enum CustomerStatus {
        ACTIVE,
       INACTIVE
    }

}

