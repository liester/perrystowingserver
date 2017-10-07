package com.perry.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/events")
public class ServerEventsController {

	private SecureRandom random = new SecureRandom();

	public String nextRandomId() {
		return new BigInteger(130, random).toString(32);
	}

	private Map<String, SseEmitter> emitterMap = new HashMap<>();

	@GetMapping(value = "/subscribe")
	public SseEmitter subscribe() {
		SseEmitter sseEmitter = new SseEmitter(-1L);
		emitterMap.put(nextRandomId(), sseEmitter);
		return sseEmitter;
	}

	@GetMapping(value = "/update/{value}")
	public void updateClients(@PathVariable String value) throws IOException {
		Set<String> keys = emitterMap.keySet();
		for (String key : keys) {
			try {
				SseEmitter emitter = emitterMap.get(key);
				emitter.send(value);
			} catch (Exception e) {
				System.out.println("Exception for Emitter:"+key);
				emitterMap.remove(key);
			}
		}
	}

}
