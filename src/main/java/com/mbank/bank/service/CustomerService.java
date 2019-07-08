package com.mbank.bank.service;

import com.mbank.bank.domain.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAll();
    CustomerEntity getCustomerById(Long id);
    CustomerEntity createCustomer(CustomerEntity customerEntity, String password);
    String validateVerificationToken(String token);
}
