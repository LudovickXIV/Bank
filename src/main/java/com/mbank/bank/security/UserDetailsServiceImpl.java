package com.mbank.bank.security;

import com.mbank.bank.domain.CredentialEntity;
import com.mbank.bank.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CredentialRepository credentialRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		CredentialEntity credential = credentialRepository.findByLogin(s)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Principal principal = new Principal();
		principal.setId(credential.getCustomerEntity().getId());
		principal.setLogin(credential.getCustomerEntity().getLogin());
		principal.setRole(credential.getCustomerEntity().getRole());
		principal.setPassword(credential.getPassword());
		return principal;
	}
}
