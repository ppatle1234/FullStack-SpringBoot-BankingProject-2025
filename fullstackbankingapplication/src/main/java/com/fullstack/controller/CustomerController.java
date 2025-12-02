package com.fullstack.controller;


import com.fullstack.entity.Customer;
import com.fullstack.service.ICustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Banking Application", description = "APIS Of Customer Controller")
@SecurityRequirement(name = "Bearer Auth")
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable long custId){
        return new ResponseEntity<>(customerService.findById(custId), HttpStatus.OK);
    }

    @PatchMapping("/deposit/{custAccountNumber}/{amount}")
    public ResponseEntity<String> depositAmount(@PathVariable long custAccountNumber, @PathVariable double amount) throws MessagingException {
        customerService.depositAmount(custAccountNumber, amount);
        return new ResponseEntity<>("Amount Deposited Successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update/{custId}")
    public ResponseEntity<Customer> update(@PathVariable long custId, @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.update(custId, customer), HttpStatus.CREATED);
    }

    @PatchMapping("/withdraw/{custAccountNumber}/{amount}")
    public ResponseEntity<String> withdrawAmount(@PathVariable long custAccountNumber, @PathVariable double amount) throws MessagingException {

        customerService.withdrawAmount(custAccountNumber, amount);

        return new ResponseEntity<>("Amount Withdraw Successfully", HttpStatus.CREATED);

    }

}
