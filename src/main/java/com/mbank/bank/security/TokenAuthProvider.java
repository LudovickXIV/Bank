package com.mbank.bank.security;

import com.mbank.bank.domain.TokenEntity;
import com.mbank.bank.exception.AuthorizationException;
import com.mbank.bank.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

	private TokenRepository tokenRepository;

	private UserDetailsService userDetailsService;

	public TokenAuthProvider(@Autowired TokenRepository tokenRepository, @Qualifier("userDetailsServiceImpl") @Autowired UserDetailsService userDetailsService) {
		this.tokenRepository = tokenRepository;
		this.userDetailsService = userDetailsService;
	}


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken;
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			AuthorisationToken auth = (AuthorisationToken) authentication;
			TokenEntity tokenEntity = tokenRepository.findByToken(auth.getToken())
					.orElseThrow(() -> new AuthorizationException("Bad token"));
			Principal principal = (Principal) userDetailsService.loadUserByUsername(tokenEntity.getCustomerEntity().getLogin());
			authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		} else {
			authenticationToken = (UsernamePasswordAuthenticationToken) currentAuth;
		}
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthorisationToken.class.isAssignableFrom(authentication);
	}
}
