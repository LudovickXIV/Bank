package com.mbank.bank.service.impl;

import com.mbank.bank.domain.CredentialEntity;
import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.Role;
import com.mbank.bank.domain.VerificationToken;
import com.mbank.bank.repository.CredentialRepository;
import com.mbank.bank.repository.CustomerRepository;
import com.mbank.bank.repository.VerificationRepository;
import com.mbank.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public List<CustomerEntity> getAll() {
        return null;
    }

    @Override
    public CustomerEntity getCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customerEntity, String password) {
        CustomerEntity savedCustomer = null;
        // TODO: 03.07.19 change this role
        customerEntity.setRole(Role.USER);
        savedCustomer = customerRepository.save(customerEntity);
        CredentialEntity credentailsEntity = new CredentialEntity();
        credentailsEntity.setLogin(savedCustomer.getLogin());
        credentailsEntity.setPassword(passwordEncoder.encode(password));
        credentailsEntity.setCustomerEntity(savedCustomer);
        credentialRepository.save(credentailsEntity);
        return savedCustomer;
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if (verificationToken == null) return TOKEN_INVALID;
        CustomerEntity customerEntity = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime()
                - calendar.getTime().getTime()) <= 0) {
            verificationRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        customerEntity.setEnabled(true);
        customerRepository.save(customerEntity);
        return TOKEN_VALID;
    }
}
