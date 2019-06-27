package com.mbank.bank.service.impl;

import com.mbank.bank.domain.CredentialEntity;
import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.domain.Role;
import com.mbank.bank.repository.CredentialRepository;
import com.mbank.bank.repository.CustomerRepository;
import com.mbank.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        customerEntity.setRole(Role.USER);
        savedCustomer = customerRepository.save(customerEntity);

        CredentialEntity credentailsEntity = new CredentialEntity();
        credentailsEntity.setLogin(savedCustomer.getLogin());
        credentailsEntity.setPassword(passwordEncoder.encode(password));
        credentailsEntity.setCustomerEntity(savedCustomer);
        credentialRepository.save(credentailsEntity);
        return savedCustomer;
    }
}
