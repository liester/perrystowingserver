package com.perry.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.perry.domain.Location;
import com.perry.domain.truck.Truck;
import com.perry.domain.truck.TruckDomainService;
import com.perry.domain.truck.TruckId;

@RestController
@RequestMapping("/trucks")
public class TruckController {

	@Inject
	private TruckDomainService truckDomainService;

	@RequestMapping(value = "/{truckId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public Truck getJobById(@PathVariable Long truckId) {
		Truck truck = truckDomainService.getByIds(Arrays.asList(truckId)).get(0);
		return truck;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Truck createTruck(@RequestBody Truck truck) {
		Truck createdTruck = truckDomainService.create(truck);
		return createdTruck;
	}
	
	@RequestMapping(value = "/status/{truckId}/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Truck updateStatus(@PathVariable Long truckId, @PathVariable String status) {
		Truck updatedTruck = truckDomainService.updateStatus(truckId, status);
		return updatedTruck;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<Truck> getAllTrucks() {
		List<Truck> truckList = truckDomainService.getAll();
		return truckList;
	}

	@RequestMapping(value = "/available", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<Truck> getAvailableTrucks() {
		List<Truck> truckList = truckDomainService.getAvailable();
		return truckList;
	}

	@RequestMapping(value = "/ids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<TruckId> getAllTruckIds() {
		List<TruckId> ids = new ArrayList<>();
		List<Truck> truckList = truckDomainService.getAll();
		truckList.stream().forEach(truck -> ids.add(new TruckId(truck.getId(), truck.getIdentifier())));
		return ids;
	}
	
	@RequestMapping(value = "/location/{truckId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public void updateTruckLocation(@PathVariable Long truckId, @RequestBody Location location) {
		truckDomainService.updateLocation(truckId, location.getLat(), location.getLon());
	}

}
