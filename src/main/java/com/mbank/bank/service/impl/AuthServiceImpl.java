package com.mbank.bank.service.impl;

import com.mbank.bank.domain.CredentialEntity;
import com.mbank.bank.domain.CustomerEntity;
import com.mbank.bank.dto.ChangePasswordDto;
import com.mbank.bank.dto.CustomerPasswordDto;
import com.mbank.bank.exception.AuthorizationException;
import com.mbank.bank.repository.CredentialRepository;
import com.mbank.bank.repository.CustomerRepository;
import com.mbank.bank.security.Principal;
import com.mbank.bank.service.AuthService;
import com.mbank.bank.service.TokenService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String generateAccessToken(CustomerPasswordDto usernamePassword) throws NotFoundException {
        CredentialEntity credentialEntity = credentialRepository
                .findByLogin(usernamePassword.getUsername().toLowerCase())
                .orElseThrow(() -> new NotFoundException("User with login= " + usernamePassword.getUsername()+ " not found"));
        validateUserPassword(usernamePassword.getPassword(), credentialEntity.getPassword());
        CustomerEntity customerEntity = credentialEntity.getCustomerEntity();
        return tokenService.createUserToken(customerEntity);
    }

    private void validateUserPassword(String incomingPassword, String storedPassword) {
        boolean matches = passwordEncoder.matches(incomingPassword, storedPassword);
        if (!matches) throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public void invalidateToken(String token) {
        tokenService.findByToken(token).ifPresent(tokenEntity -> tokenService.delete(tokenEntity));
    }

    @Override
    public void changePassword(ChangePasswordDto passwordDto) {
        Principal principal = (Principal) getAuthPrincipal();
        CustomerEntity customerEntity = customerRepository.findById(principal.getId()).get();
        CredentialEntity credentialEntity = credentialRepository.findByLogin(customerEntity.getLogin().toLowerCase()).get();

        if (passwordEncoder.matches(passwordDto.getOldPassword(), credentialEntity.getPassword())) {
            credentialEntity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            credentialRepository.save(credentialEntity);
            tokenService.deleteTokenByUser(customerEntity);
        } else {
            throw new AuthorizationException("Password doen't match");
        }
    }
}
