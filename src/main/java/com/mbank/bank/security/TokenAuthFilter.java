package com.mbank.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends AbstractAuthenticationProcessingFilter {

	private String tokenPrefix;

	public TokenAuthFilter(@Autowired AuthenticationManager authenticationManager,
							  @Value("${security.token-prefix}") String tokenPrefix) {
		super("/**");
		setAuthenticationManager(authenticationManager);
		this.tokenPrefix = tokenPrefix;
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		boolean a = super.requiresAuthentication(request, response);
		boolean b = StringUtils.startsWithIgnoreCase(authHeader, tokenPrefix);
		return a && b;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
												HttpServletResponse httpServletResponse)
			throws AuthenticationException {
		String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
		String token = extractToken(authHeader);

		AuthorisationToken authorisationToken = AuthorisationToken.of(token);
		Authentication authenticationResult = getAuthenticationManager().authenticate(authorisationToken);

		return authenticationResult;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	private String extractToken(String authHeader) {
		return authHeader.substring(tokenPrefix.length() + 1);
	}
}
