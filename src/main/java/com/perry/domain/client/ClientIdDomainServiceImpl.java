package com.perry.domain.client;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.perry.domain.ClientId;
import com.perry.infrastructure.client.ClientIdDaoService;

@Named
public class ClientIdDomainServiceImpl implements ClientIdDomainService{
	
	@Inject
	private ClientIdDaoService clientIdDaoService;

	@Override
	public List<ClientId> getAll() {
		List<ClientId> clientIds = clientIdDaoService.getAll();
		return clientIds;
	}

}
