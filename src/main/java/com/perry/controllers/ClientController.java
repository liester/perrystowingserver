package com.perry.controllers;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<ClientId>> getAllClients() throws IOException {
		List<ClientId> clientIds = clientIdDomainService.getAll();
		return new ResponseEntity<List<ClientId>>(clientIds, HttpStatus.OK);
	};

	// Below was a Dream....A Dream that was Rome (Server Sent Events)
	//
	// private List<SseEmitter> sseEmitters = Collections.synchronizedList(new
	// ArrayList<>());
	//
	// @RequestMapping("/register")
	// public ResponseEntity<SseEmitter> handleRequest() throws IOException {
	//
	// SseEmitter sseEmitter = new SseEmitter();
	// this.sseEmitters.add(sseEmitter);
	// sseEmitter.onCompletion(() -> {
	// synchronized (this.sseEmitters) {
	// this.sseEmitters.remove(sseEmitter);
	// }
	// });
	// return new ResponseEntity<SseEmitter>(sseEmitter, HttpStatus.OK);
	// }
	//
	// @RequestMapping("/message")
	// public void messageClients() throws IOException {
	//
	// for (SseEmitter sseEmitter : this.sseEmitters) {
	// // Servlet containers don't always detect ghost connection, so we must
	// catch exceptions ...
	// try {
	// sseEmitter.send(new Date().toString(), MediaType.APPLICATION_JSON);
	// } catch (Exception e) {
	// }
	// }
	//
	// }

}
