package com.perry.domain.client;

import java.util.List;

import com.perry.domain.ClientId;

public interface ClientIdDomainService {

	public List<ClientId> getAll();

	public List<ClientId> updateAll(List<ClientId> clientIds);
	
	public void deleteById(long id);
	
	public ClientId getByClientId(String clientId);

}
