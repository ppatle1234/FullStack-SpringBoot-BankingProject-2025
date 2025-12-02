package com.fullstack.service;

import com.fullstack.entity.Customer;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface ICustomerService {

    Customer signUp(Customer customer);


    void depositAmount(long custAccountNumber, double amount) throws MessagingException;

    Customer update(long custId, Customer customer);


    Optional<Customer> findById(long custId);

    void withdrawAmount(long custAccountNumber, double amount) throws MessagingException;

}