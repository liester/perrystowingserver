package com.perry.domain.authentication;

import javax.inject.Inject;
import javax.inject.Named;

import com.perry.infrastructure.authentication.AuthenticationDaoService;

@Named
public class AuthenticationDomainServiceImpl implements AuthenticationDomainService{
	
	@Inject
	private AuthenticationDaoService authenticationDaoService; 

	@Override
	public boolean isValidClient(String clientId) {
		boolean isValidClient = authenticationDaoService.isValidClient(clientId);
		return isValidClient;
	}

}
