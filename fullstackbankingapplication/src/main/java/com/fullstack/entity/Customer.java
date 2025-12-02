package com.fullstack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long custId;

    private long custAccountNumber;

    @Size(min = 2, message = "Customer Name should be at least 2 characters")
    private String custName;

    @NotBlank(message = "Address should not be blank or empty")
    private String custAddress;

    @Range(min = 1000000000, max = 9999999999L, message = "Contact Number must be 10 Digit")
    private long custContactNumber;

    private double custAccountBalance;

    private String custGender;

    private LocalDate custDOB;

    private String custPanCard;

    private long custUID;

    @Email(message = "Email ID Must be valid")
    private String custEmailId;

    @Size(min = 4, message = "Password should be 4 characters")
    private String custPassword;


}