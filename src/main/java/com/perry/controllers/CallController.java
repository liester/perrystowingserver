package com.perry.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perry.domain.CallType;
import com.perry.domain.Customer;
import com.perry.domain.KeyLocationType;
import com.perry.domain.Vehicle;
import com.perry.domain.call.Call;
import com.perry.domain.call.CallDomainService;
import com.perry.domain.truck.Truck;
import com.perry.domain.truck.TruckDomainService;

@RestController
@RequestMapping("/calls")
public class CallController {

	@Inject
	private CallDomainService callDomainService;
	@Inject
	private TruckDomainService truckDomainService;

	@RequestMapping(value = "/{callId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public Call getJobById(@PathVariable Long callId) {
		List<Call> callList = callDomainService.getByIds(Arrays.asList(callId));
		return callList.get(0);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Call create(@RequestBody Call call) {
		Call createdCall = callDomainService.create(call);
		return createdCall;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Call edit(@RequestBody Call call) {
		Call createdCall = callDomainService.edit(call);
		return createdCall;
	}

	@RequestMapping(value = "/delete/{callId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public void create(@PathVariable long callId) {
		callDomainService.delete(callId);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<Call> getAllCalls() {
		List<Call> callList = callDomainService.getAllCalls();
		return callList;
	}

	// @RequestMapping(value = "/available", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<Call> getAvailableCalls() {
		List<Call> callList = callDomainService.getAvailable();
		return callList;
	}

	@RequestMapping(value = "/assign/{callId}/{truckId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Truck> assignTruck(@PathVariable long callId, @PathVariable long truckId) {
		Truck updatedTruck = callDomainService.assignTruck(callId, truckId);
		return new ResponseEntity<Truck>(updatedTruck, HttpStatus.OK);

	}

	@RequestMapping(value = "/unassign/{callId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public void unAssignTruck(@PathVariable long callId) {
		callDomainService.unAssignTruck(callId);

	}

	@RequestMapping(value = "/activeTruck/{truckId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public Call getTruckActive(@PathVariable long truckId) {
		return callDomainService.getTruckActive(truckId);
	}

	@RequestMapping(value = "/activeTruck/{truckId}/complete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public void completeActiveTruckCall(@PathVariable long truckId) {
		Call currentCall = callDomainService.getTruckActive(truckId);
		truckDomainService.updateStatus(truckId, "Available");
		callDomainService.unAssignTruck(currentCall.getId());

		currentCall.setTruckId(-1);
		callDomainService.edit(currentCall);
	}

	@RequestMapping(value = "/nonComplete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<Call> getNonComplTruckActive() {
		return callDomainService.getAllNonCompleteCalls();
	}
}
