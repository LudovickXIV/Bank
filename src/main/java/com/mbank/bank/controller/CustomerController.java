package com.mbank.bank.controller;

import com.mbank.bank.OnRegistrationCompleteEvent;
import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.dto.CustomerDTO;
import com.mbank.bank.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/registration")
    public ResponseEntity<CustomerDTO> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO, HttpServletRequest request) {
        final CustomerEntity customerEntity = customerService.createCustomer(mapper.map(customerDTO, CustomerEntity.class), customerDTO.getPassword());
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(customerEntity, getAppUrl(request)));
        System.out.println("EVENT!");
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(value = "/registrationConfirm")
    public ResponseEntity<CustomerDTO> registrationConfirm(HttpServletRequest request, @RequestParam("token") String token) {
        String result = customerService.validateVerificationToken(token);
        return ResponseEntity.ok(null);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
