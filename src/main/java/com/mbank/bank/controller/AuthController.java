package com.mbank.bank.controller;

import com.mbank.bank.dto.CustomerPasswordDto;
import com.mbank.bank.dto.TokenDto;
import com.mbank.bank.service.AuthService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private String tokenPrefix;

    public AuthController(@Autowired AuthService authService,
                          @Value("${security.token-prefix}") String tokenPrefix) {
        this.authService = authService;
        this.tokenPrefix = tokenPrefix + " ";
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody CustomerPasswordDto customerPassword, HttpServletRequest request)
            throws NotFoundException {
        String token = authService.generateAccessToken(customerPassword);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(tokenPrefix + token);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!StringUtils.isEmpty(token)) {
            authService.invalidateToken(token.replace(this.tokenPrefix, ""));
        }
        return ResponseEntity.ok().build();
    }
}
