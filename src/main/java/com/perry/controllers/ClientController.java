package com.perry.controllers;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perry.domain.ClientId;
import com.perry.domain.client.ClientIdDomainService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Inject
	private ClientIdDomainService clientIdDomainService;

	@RequestMapping("")
	public ResponseEntity<List<ClientId>> getAllClientIds() throws IOException {
		List<ClientId> clientIds = clientIdDomainService.getAll();
		return new ResponseEntity<List<ClientId>>(clientIds, HttpStatus.OK);
	};
	
	
	@RequestMapping("/update")
	public ResponseEntity<List<ClientId>> updateClientIds(@RequestBody List<ClientId> clientIds) throws IOException {
		List<ClientId> clientIdReturnList = clientIdDomainService.updateAll(clientIds);
		return  new ResponseEntity<List<ClientId>>(clientIdReturnList, HttpStatus.OK);
	};

}
