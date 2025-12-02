package com.fullstack.service;

import com.fullstack.entity.Customer;
import com.fullstack.exception.InsufficientFundException;
import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.repository.CustomerRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService implements ICustomerService, UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String userName;


    @Override
    public Customer signUp(Customer customer) {

        customer.setCustPassword(passwordEncoder.encode(customer.getCustPassword()));

        return customerRepository.save(customer);

    }

    @Override
    public void depositAmount(long custAccountNumber, double amount) throws MessagingException {

        Customer customer = customerRepository.findByCustAccountNumber(custAccountNumber).orElseThrow(() -> new RecordNotFoundException("Customer Account Does Not Exist"));

        double custAccountBalance = customer.getCustAccountBalance();

        custAccountBalance += amount;
        customer.setCustAccountBalance(custAccountBalance);

        customerRepository.save(customer);


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(userName);

        mimeMessageHelper.setTo(customer.getCustEmailId());

        mimeMessageHelper.setSubject("Amount Deposited");

        mimeMessageHelper.setText("After Deposit Customer Account Balance: " + custAccountBalance);

        javaMailSender.send(mimeMessage);

        log.info("Mail Sent Successfully");
    }

    @Override
    public Customer update(long custId, Customer customer) {

        Customer customer1 = customerRepository.findById(custId).orElseThrow(() -> new RecordNotFoundException("Customer ID Does Not Exist"));

        customer1.setCustName(customer.getCustName());
        customer1.setCustEmailId(customer.getCustEmailId());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustAccountBalance(customer.getCustAccountBalance());
        customer1.setCustContactNumber(customer.getCustContactNumber());
        customer1.setCustGender(customer.getCustGender());
        customer1.setCustAccountNumber(customer.getCustAccountNumber());
        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustPanCard(customer.getCustPanCard());
        customer1.setCustUID(customer.getCustUID());
        customer1.setCustPassword(customer.getCustPassword());

        return customerRepository.save(customer1);
    }

    @Override
    public Optional<Customer> findById(long custId) {
        return Optional.ofNullable(customerRepository.findById(custId).orElseThrow(() -> new RecordNotFoundException("Customer ID Does Not Exist")));

    }

    @Override
    public void withdrawAmount(long custAccountNumber, double amount) throws MessagingException {

        Customer customer = customerRepository.findByCustAccountNumber(custAccountNumber).orElseThrow(() -> new RecordNotFoundException("Customer Account Does Not Exist"));

        double custAccountBalance = customer.getCustAccountBalance();

        if (customer.getCustAccountBalance() >= amount) {
            custAccountBalance -= amount;
        } else {


            throw new InsufficientFundException("Insufficient Fund");


        }
        customer.setCustAccountBalance(custAccountBalance);

        customerRepository.save(customer);


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(userName);

        mimeMessageHelper.setTo(customer.getCustEmailId());

        mimeMessageHelper.setSubject("Amount Withdraw");

        mimeMessageHelper.setText("After Withdraw Customer Account Balance: " + custAccountBalance);

        javaMailSender.send(mimeMessage);

        log.info("Mail Sent Successfully");

    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByCustEmailId(username);


        return new User(customer.getCustEmailId(), customer.getCustPassword(), new ArrayList<>());
    }
}