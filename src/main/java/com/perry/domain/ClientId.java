package com.perry.domain;

public class ClientId {
	private long id;

	private String clientId;

	private long role;

	public ClientId() {
	}

	public ClientId(long id, String clientId, long role) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getRole() {
		return role;
	}

	public void setRole(long role) {
		this.role = role;
	}

}
