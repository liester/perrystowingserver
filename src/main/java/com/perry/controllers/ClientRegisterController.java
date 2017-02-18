package com.perry.controllers;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ClientRegisterController {

	private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	@RequestMapping("/register")
	public ResponseEntity<SseEmitter> handleRequest() throws IOException {

		SseEmitter sseEmitter = new SseEmitter();
		this.sseEmitters.add(sseEmitter);
		sseEmitter.onCompletion(() -> {
			synchronized (this.sseEmitters) {
				this.sseEmitters.remove(sseEmitter);
			}
		});
		return new ResponseEntity<SseEmitter>(sseEmitter, HttpStatus.OK);
	}

	@RequestMapping("/message")
	public void messageClients() throws IOException {

		for (SseEmitter sseEmitter : this.sseEmitters) {
			// Servlet containers don't always detect ghost connection, so we must catch exceptions ...
			try {
				sseEmitter.send(new Date().toString(), MediaType.APPLICATION_JSON);
			} catch (Exception e) {
			}
		}

	}

}
