package com.perry.infrastructure.client;

import java.util.List;

import com.perry.domain.ClientId;

public interface ClientIdDaoService {

	public List<ClientId> getAll();

	public List<ClientId> updateAll(List<ClientId> clientIds);
}
