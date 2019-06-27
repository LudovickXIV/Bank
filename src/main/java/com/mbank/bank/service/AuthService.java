package com.mbank.bank.service;

import com.mbank.bank.dto.ChangePasswordDto;
import com.mbank.bank.dto.CustomerPasswordDto;
import javassist.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    default UserDetails getAuthPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    String generateAccessToken(CustomerPasswordDto usernamePassword) throws NotFoundException;
    void invalidateToken (String token);
    void changePassword (ChangePasswordDto passwordDto);
}
