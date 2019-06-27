package com.mbank.bank.controller;

import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.dto.CustomerDTO;
import com.mbank.bank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        System.out.println("ADDED");
        customerService.createCustomer(mapper.map(customerDTO, CustomerEntity.class), customerDTO.getPassword());
        return ResponseEntity.ok(customerDTO);
    }
}
